package com.dajie.wika.service.utils;

import java.util.Properties;

public class ConfigUtil {

    private  Properties tgProperties = new Properties();

    private static final String PROPERTY_PATH = "config.properties";
    
    private static ConfigUtil configUtil=null;

    private ConfigUtil() {
        try {
             tgProperties.load(this.getClass().getResourceAsStream(PROPERTY_PATH)); 
        } catch ( Exception e ) {
           
        }
    }

    public static final ConfigUtil getInstance () {
            if(configUtil==null)
                    configUtil=new ConfigUtil();
        return configUtil;
    }

    
    public  String getConfig (String propertyName) {
            
        return tgProperties.getProperty(propertyName);
    }
    
    public static void main(String[] args) {
                System.out.println(ConfigUtil.getInstance().getConfig("test"));
     }
}
