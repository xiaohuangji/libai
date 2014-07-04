package com.dajie.wika.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.common.dubbo.tolerance.Idempotent;
import com.dajie.wika.constants.GenderType;
import com.dajie.wika.dao.QRcodeDAO;
import com.dajie.wika.model.UserAvatarSet;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserQRSet;
import com.dajie.wika.qrcode.QRCodeFactory;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.QRcodeService;
import com.dajie.wika.service.ResourceService;
import com.dajie.wika.service.ShortUrlService;
import com.dajie.wika.service.utils.PicUrls;

@Service("qRcodeService")
public class QRcodeServiceImpl implements QRcodeService{

	private Logger logger = LoggerFactory.getLogger(QRcodeServiceImpl.class);
	
	@Autowired
	private ShortUrlService shortUrlService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private QRcodeDAO qRcodeDAO;
	
	/**
	 * 默认要生成qr的模板id
	 */
	private static final int[] TIDS={3,7,8};
	
	private static final int MALE_DEFAULT_TID=1;
	
	private static final int FEMALE_DEFAULT_TID=2;
	/**
	 * 更新头像触发此动作
	 * 这页代码各种拷贝，真尼玛恶心
	 * @throws IOException 
	 */
	@Override
	@Idempotent(value = false)
	public UserAvatarSet genQRcode(int userId, String avatar,int gender) {
		try {
			UserAvatarSet avatarSet=new UserAvatarSet(userId, avatar);
			//获取二维码的信息
			String qrContent=shortUrlService.genWikaProfileShortUrl(userId);
			if(avatar.indexOf("dajieimg.com")!=-1){//原始头像,需要转换
				avatar=new PicUrls(avatar).getL();
			}
			//生成默认模板
			//int gender=accountService.getUserBaseById(userId).getGender();
			int defaultTid=MALE_DEFAULT_TID;
			if(gender==GenderType.FEMALE){
				defaultTid=FEMALE_DEFAULT_TID;
			}
			
			byte defaultData[]=null;
			logger.info("start gen default qrcode,avatar: "+avatar);
			
			//将图片url转成内存图片
	        BufferedImage image = ImageIO.read(new URL(avatar));
			defaultData = QRCodeFactory.makeQrcode(defaultTid, qrContent, image);
			
			if(defaultData==null){
				logger.warn("make default qrcode error");
				return avatarSet;
			}
			
			String defaultQrUrl=resourceService.uploadFile(defaultData, "1.jpg", 2);
			avatarSet.setQrAvatar(defaultQrUrl);
			avatarSet.setQrId(defaultTid);
			
			//异步生成其他qr模板
			ExecutorService executor = Executors.newCachedThreadPool();
			FutureTask<Integer> future = new FutureTask<Integer>(
				new AsynGenQRcode(userId, gender,defaultTid, defaultQrUrl, qrContent, image)
			);
			executor.execute(future);
			//只返回默认的qrcode
			return avatarSet;
		}catch (IOException e1) {
			logger.warn("make qr code error:"+avatar,e1);
			return null;
		}
		
	}
	@Override
	public List<UserQRSet> getAllQRcode(int userId) {
		//如果获取不到脸码，触发生成动作
		List<UserQRSet> userQRSets=qRcodeDAO.getQRcode(userId);
		if(userQRSets==null||userQRSets.size()==0){
			logger.error("could not find qrcode and then asyn gencode:"+userId);
			UserBase userBase=accountService.getUserBaseById(userId);
			String avatar=userBase.getAvatar();
			if(avatar!=null){
				genQRcode(userId,avatar,userBase.getGender());
			}	
		}
		return userQRSets;
	}
	


	@Override
	public String getQRcodeUrl(int userId, int qrId) {
		// TODO Auto-generated method stub
		return qRcodeDAO.getQRcodeUrl(userId, qrId);
	}

	public class AsynGenQRcode implements Callable<Integer>{

		/**
		 * 用户id
		 */
		private int userId;
		
		/**
		 * 性别
		 */
		private int gender;
		/**
		 * 默认使用的脸码模板
		 */
		private int defaultTid;
		
		/**
		 * 默认的脸码url
		 */
		private String defaultQrUrl;
		
		/**
		 * 脸码内容
		 */
		private String qrContent;
		
		/**
		 * 头像图片内存流
		 */
		private BufferedImage avatar;
		
		public AsynGenQRcode(int userId,int gender,int defaultTid,String defaultQrUrl,String qrContent,BufferedImage avatar){
			this.userId=userId;
			this.gender=gender;
			this.defaultTid=defaultTid;
			this.defaultQrUrl=defaultQrUrl;
			this.qrContent=qrContent;
			this.avatar=avatar;
		}
		
		@Override
		public Integer call() throws Exception {
			List<UserQRSet> userQRSets=new ArrayList<UserQRSet>();
    		userQRSets.add(new UserQRSet(userId,defaultTid, defaultQrUrl));
    		
    		int otherDefaultTid=FEMALE_DEFAULT_TID;
			if(gender==GenderType.FEMALE){
				otherDefaultTid=MALE_DEFAULT_TID;
			}
			byte[] data=QRCodeFactory.makeQrcode(otherDefaultTid, qrContent, avatar);
			if(data!=null){
				String qrUrl=resourceService.uploadFile(data, "1.jpg", 2);
				if(qrUrl!=null){
					userQRSets.add(new UserQRSet(userId,otherDefaultTid, qrUrl));
				}
			}
			
    		for(int id:TIDS){
    			try {
    				data=QRCodeFactory.makeQrcode(id, qrContent, avatar);
    				if(data!=null){
    					//将生成的二维码上到文件系统。默认都是jpg格式，2表示压缩类型qr头像
    					String qrUrl=resourceService.uploadFile(data, "1.jpg", 2);
    					if(qrUrl!=null){
    						userQRSets.add(new UserQRSet(userId,id, qrUrl));
    					}
    				}
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    		//将生成的脸码保存到数据库
    		return qRcodeDAO.insertQRcode(userQRSets);
		}
		
	}

}
