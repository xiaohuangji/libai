/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.runner;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 这是一个Runner的Demo
 * 
 * @author wei.cheng
 * 
 */
public class DemoRunner {

    private static final Logger logger = LoggerFactory.getLogger(DemoRunner.class);

    public static void main(String[] args) {

        test2(args);

    }

    public static void test1(String[] args) {
        // 加载配置
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/*.xml");

        // 获取需要的bean
        AbstractMessageSource ps = (AbstractMessageSource) ctx.getBean("messageSource");

        // 执行业务
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("[DemoRunner]:[aaaaaaaaaaaa]"));
        }
        // ResourceBundle rb = ps.getResourceBundle("api_result_code_messages",
        // Locale.CHINA);
        // ResourceBundleMessageSource rbs = (ResourceBundleMessageSource) ps;
        System.out.println(ps.getMessage("api.result.msg.1", null, Locale.CHINA));
    }

    public static void test2(String[] args) {
        System.out.println(System.getProperties().getProperty("java.version"));
    }
}
