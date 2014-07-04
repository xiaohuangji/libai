package com.dajie.wika.wap.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dajie.wika.wap.util.HttpRequestUtil;

@Controller
@RequestMapping("h")
public class HelloWorldController {

	
    Logger logger=Logger.getLogger(HelloWorldController.class);
    
    
	@RequestMapping("w")
	public ModelAndView world(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView(HttpRequestUtil.addStatInfo(request, "world"));
		return modelAndView;
	}
	
	@RequestMapping("s")
	public ModelAndView submit(HttpServletRequest request, @RequestParam("name") String name,@RequestParam("depa") String depa,@RequestParam("sex") String sex,@RequestParam("mobile") String mobile,@RequestParam("fb") String fb){
		ModelAndView modelAndView = new ModelAndView(HttpRequestUtil.addStatInfo(request, "submit"));
		
		File f = new File("e:\\sss.log");
		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true),"UTF-8");
			BufferedWriter writer=new BufferedWriter(write);  
			String fbutf=new String(fb.getBytes("ISO-8859-1"), "UTF-8");
			String nameutf=new String(name.getBytes("ISO-8859-1"), "UTF-8");
			String depautf=new String(depa.getBytes("ISO-8859-1"), "UTF-8");
			String sexutf=new String(sex.getBytes("ISO-8859-1"), "UTF-8");
			String mobileutf=new String(mobile.getBytes("ISO-8859-1"), "UTF-8");
			
			String ssString="==>"+nameutf+"   "+depautf+"   "+sexutf+"   "+mobileutf+"  "+fbutf+" "+new Date();
			//System.out.println(ssString);
			writer.write(ssString);
			writer.newLine();
			writer.close();
			
		} catch (IOException e) {
			
		}
		return modelAndView;
	}
	
}

