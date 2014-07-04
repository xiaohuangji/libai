/**
 * 
 */
package com.dajie.wika.fdfs.client;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于fastDFS的resourceManager实现
 * @author <a href='mailto:lizheng8318@gmail.com'>lizheng</a>
 *
 */
public class FastDFSResourceManager{
	
	private final Logger logger = LoggerFactory.getLogger(FastDFSResourceManager.class);

	/** fastDFS TrackerServer pool*/
	private FastDFSTrackerServerConnectionProvider trackerProvider;
	
	/**
	 * 
	 */
	public FastDFSResourceManager() {
	}


	/* (non-Javadoc)
	 * @see com.dajie.framework.res.api.ResourceManager#uploadResource(java.lang.String, byte[])
	 */
	public String uploadResource(String contentType, byte[] resource){
		TrackerServer trackerServer = null;
		try {
			// 上传文件
			
			trackerServer = trackerProvider.getConnection();
			logger.debug("FastDFS, conection... {};", trackerServer.getInetSocketAddress());
			
	        StorageClient client = new StorageClient(trackerServer, null);	// 此构造方法中的StorageServer传值为null，client会自动创建StorageServer，并且自动释放。如果手工传入该参数，则需要手工关闭StorageServer的连接 
	        
	        String[] results = client.upload_file(resource, contentType, null);
	        
			if (results == null) {
				logger.error("FastDFS, upload... error, error code:{};", client.getErrorCode());
				return null;
			} else {
				logger.debug("FastDFS, upload... group:{};filename:{};", results[0], results[1]);
				// 拼接域名
		        int di = Double.valueOf(Math.random() * 10).intValue();
				return "http://" + di + ".f1.dajieimg.com/" + results[0] + "/" + results[1];
			}
		} catch (IOException e) {
			logger.error("fastDFS error!", e);
			// 这里出现io exception，有两种可能，一种，storage server无法连接，另外一种，tracker server无法连接
			// 因为tracker server使用的连接池，如果没有开启test_on_borrow参数，则无法检查tracker server是否可用
			// 这里强制释放一下该连接
			trackerProvider.invalidateCon(trackerServer);
			return null;
		} catch (MyException e) {
			logger.error("fastDFS error!", e);
			return null;
		} finally {
			if (trackerServer != null) {
				trackerProvider.returnCon(trackerServer);
				logger.debug("FastDFS, conection close; {};", trackerServer.getInetSocketAddress());
			}
		}
	}

	/**
	 * @return the trackerProvider
	 */
	public FastDFSTrackerServerConnectionProvider getTrackerProvider() {
		return trackerProvider;
	}

	/**
	 * @param trackerProvider the trackerProvider to set
	 */
	public void setTrackerProvider(
			FastDFSTrackerServerConnectionProvider trackerProvider) {
		this.trackerProvider = trackerProvider;
	}
	
}
