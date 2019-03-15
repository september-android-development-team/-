package com.test.september.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String md5(String str) {
        MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            } catch (NoSuchAlgorithmException e) {
                System.out.println("NoSuchAlgorithmException caught!");
                System.exit(-1);
            }
        byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            return md5StrBuff.toString();
}

//    public static String md5(String txt) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            // 问题主要出在这里，Java的字符串是unicode编码，不受源码文件的编码影响；而PHP的编码是和源码文件的编码一致，受源码编码影响。
//            md.update(txt.getBytes("GBK"));
//            StringBuffer buf = new StringBuffer();
//            for (byte b : md.digest()) {
//                buf.append(String.format("%02x", b & 0xff));
//            }
//            return buf.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
