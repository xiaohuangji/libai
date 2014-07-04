package com.dajie.wika.wap.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dajie.wika.wap.model.Industry;
import com.dajie.wika.wap.model.JobType;

public class WikaDataUtil {

	private static final Logger logger=LoggerFactory.getLogger(WikaDataUtil.class);
	
	private static String absolutePath =Thread.currentThread()
            .getContextClassLoader().getResource("").getPath();
	
	private static Industry industry=null;
	
	private static JobType jobType=null;
	
	private static final String industryFile="industry.txt";
	
	private static final String jobTypeFile="job_type.txt";
	
	private static Object loadIndustry(String filename ,Class clazz) {
		try{
			File file = new File(absolutePath+filename);
			StringBuilder sb = new StringBuilder();
			String s ="";
			
			InputStreamReader read = new InputStreamReader (new FileInputStream(file),"UTF-8");
			BufferedReader br = new BufferedReader(read);
			while( (s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			String str = sb.toString();
			return WikaJsonUtil.fromJson(str, clazz);
		}catch(Exception e){
			logger.error("load file error",e);
			return null;
		}
	}
	
	public static Industry getIndustry() {
		if(industry==null){
			industry=(Industry)loadIndustry(industryFile,Industry.class);
		}
		return industry;	
	}
	
	public static JobType getJobType() {
		if(jobType==null){
			jobType=(JobType)loadIndustry(jobTypeFile,JobType.class);
		}
		return jobType;	
	}
	
	public static void main(String[] args) {
		JobType t=WikaDataUtil.getJobType();
		System.out.println(t.getData());
	}
}
