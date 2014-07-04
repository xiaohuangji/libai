package com.dajie.wika.wap.util;

import java.math.BigInteger;  
 
public class EncrUtil {  
    private static final int RADIX = 32;  
    private static final String SEED = "6937343168828";//随手找了串数字。来自慢严舒柠的条形码
  
    public static final String encrypt(String password) {  
        if (password == null)  
            return "";  
        if (password.length() == 0)  
            return "";  
  
        BigInteger bi_passwd = new BigInteger(password.getBytes());  
  
        BigInteger bi_r0 = new BigInteger(SEED);  
        BigInteger bi_r1 = bi_r0.xor(bi_passwd);  
  
        return bi_r1.toString(RADIX);  
    }  
  
    public static final String decrypt(String encrypted) {  
        if (encrypted == null)  
            return "";  
        if (encrypted.length() == 0)  
            return "";  
  
        BigInteger bi_confuse = new BigInteger(SEED);  
  
        try {  
            BigInteger bi_r1 = new BigInteger(encrypted, RADIX);  
            BigInteger bi_r0 = bi_r1.xor(bi_confuse);  
  
            return new String(bi_r0.toByteArray());  
        } catch (Exception e) {  
            return "";  
        }  
    }  
  
    public static void main(String args[]){  
    	//String userId="123456";
    	for(int i=10000000;i<99999999;i++){
    		if(!Integer.valueOf(EncrUtil.decrypt(EncrUtil.encrypt(String.valueOf(i)))).equals(i))
    			System.out.println("nononono");
			
    	}
    	
    }  
      
}  
