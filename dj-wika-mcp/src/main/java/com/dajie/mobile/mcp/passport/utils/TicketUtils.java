/**
 * 
 */
package com.dajie.mobile.mcp.passport.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * head(8)|uniqueKey(128)[userId(32)appId(32)]|jam(1)
 * 
 * @author wei.cheng
 * 
 */
public class TicketUtils {

    private static final Logger logger = LoggerFactory.getLogger(TicketUtils.class);

    //    private final static String KEY = "((*!adfeew12342adEJU78";
    private static final String T_VERSION = "A";

    public static String generateTicket(int userId) {
        if (userId <= 0) {
            return null;
        }

        Random rd = new Random();
        long rdLong = rd.nextLong();
        int safe = 0;
        while (rdLong < 1000 && safe < 100) {
            rdLong = rd.nextLong();
            safe++;
        }
        long now = System.currentTimeMillis();
        String nowStr = now + "";
        nowStr = nowStr.substring(4);
        String rdLongStr = rdLong + "";
        rdLongStr = rdLongStr.substring(rdLongStr.length() - 4);
        //        UUID uuid = UUID.randomUUID();
        //        String uuidStr = uuid.toString().replace("-", "");
        //        System.out.println(T_VERSION+rdLongStr + nowStr + userId + "");
        String mix = mixContent(T_VERSION + rdLongStr + nowStr, userId + "");
        //        System.out.println(mix);
        //        System.out.println(getUserIdFromMix(mix));
        //        byte[] eb = encryptAES(mix, KEY);

        String ticket = null;
        try {
            ticket = T_VERSION + Base64.encodeBase64String(mix.getBytes("UTF-8"));
            ticket = ticket.replace("\n", "").replace("\r", "").replace("/", "-").replace("+", "_")
                    .replace("=", ".");
        } catch (Exception e) {
            logger.error("generateTicket", e);
        }

        return ticket;
    }

    public static int decryptTicket(String ticket) {
        int userId = 0;
        if (StringUtils.isEmpty(ticket)) {
            return userId;
        }
        try {
            ticket = ticket.substring(1);
            ticket = ticket.replace("-", "/").replace("_", "+").replace(".", "=");
            byte[] eb = Base64.decodeBase64(ticket);
            //            byte[] db = decryptAES(eb, KEY);
            String dStr = new String(eb, "UTF-8");

            userId = getUserIdFromMix(dStr);
        } catch (Exception e) {
            logger.error("decryptTicket", e);
        }
        return userId;
    }

    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            logger.error("serialize", e);
        }
        return null;
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }

    private static String mixContent(String maskStr, String uidStr) {
        if (maskStr.length() <= uidStr.length()) {
            return null;
        }
        char[] mix = new char[maskStr.length() + uidStr.length() + 1];
        int uidStrIdx = 0;
        int maskStrIdx = 0;
        boolean isUidFull = false;
        for (int i = 0; i < mix.length; i++) {
            if (i % 2 != 0 && uidStrIdx < uidStr.length()) {
                // 奇数位放userId
                mix[i] = uidStr.charAt(uidStrIdx);
                uidStrIdx++;
            } else {
                if (uidStrIdx == uidStr.length() && isUidFull == false) {
                    mix[i] = '_';
                    isUidFull = true;
                    continue;
                }
                mix[i] = maskStr.charAt(maskStrIdx);
                maskStrIdx++;
            }
        }

        return new String(mix);
    }

    private static int getUserIdFromMix(String mix) {
        int userId = 0;
        if (StringUtils.isEmpty(mix)) {
            return userId;
        }

        String[] uMixArr = mix.split("_");
        if (uMixArr == null || uMixArr.length != 2) {
            return userId;
        }

        String uStr = "";
        for (int i = 0; i < uMixArr[0].length(); i++) {
            if (i % 2 != 0) {
                uStr += uMixArr[0].charAt(i);
            }
        }

        userId = NumberUtils.toInt(uStr);

        return userId;
    }

    public static void main(String[] s) throws IOException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException {

        long time = System.currentTimeMillis();
        String t = generateTicket(144);
        System.out.println(t + " cost:" + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        int uid = decryptTicket(t);
        //        int uid = decryptTicket("K-nXSSav6he3oqYRVu7tRf9685btSiavEGDOEUwTZLU=");
        System.out.println(uid + " cost:" + (System.currentTimeMillis() - time));

    }

}
