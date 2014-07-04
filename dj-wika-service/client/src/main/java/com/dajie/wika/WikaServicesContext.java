package com.dajie.wika;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author: wei.cheng
 */
public class WikaServicesContext {

    private static final String[] CONFIG_FILES = new String[] {
            "classpath:dubbo_config/common.xml", "classpath:dubbo_config/common_consumer.xml",
            "classpath:dubbo_config/wika-consumer.xml", "classpath*:dubbo_config/*-app.xml" };

    private static final WikaServicesContext INSTANCE = new WikaServicesContext();

    private final ClassPathXmlApplicationContext applicationContext;

    private WikaServicesContext() {
        this.applicationContext = new ClassPathXmlApplicationContext(CONFIG_FILES);
        this.applicationContext.start();
    }

    public static WikaServicesContext getInstance() {
        return INSTANCE;
    }

}
