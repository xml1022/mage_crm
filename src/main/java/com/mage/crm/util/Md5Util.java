package com.mage.crm.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//用来对获取到的密码进行加密和解密
public class Md5Util {
    //对获取到的密码进行加密，用于判断加密过后的密码与数据库中的密码是否一致
    public static String encode(String pwd){
        try{
            MessageDigest md5 = MessageDigest.getInstance("md5");
            return Base64.encodeBase64String(md5.digest(pwd.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    return null;
    }
}
