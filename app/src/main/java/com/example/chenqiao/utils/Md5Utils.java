package com.example.chenqiao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by CHENQIAO on 2016/3/26.
 */
public class Md5Utils {

    public static  String getMd5Digest(String password) {

        String Md5pwd = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] digest = messageDigest.digest(password.getBytes());
            StringBuffer resultstring = new StringBuffer();
            for(byte b : digest){
                int result = b&0xff;
                String hexString = Integer.toHexString(result);
                if (hexString.length() == 1){
                    resultstring.append(0);
                }
                resultstring.append(hexString);
                Md5pwd =  resultstring.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Md5pwd;
    }

}
