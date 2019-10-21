package com.carlinx.shiro.utils;

import java.util.Base64;

public class Base64Util {

    /**
     * 加密
     * @param content
     * @return
     */
    public static String encode(String content){
        try {
            byte[] encodeBytes = Base64.getEncoder().encode(content.getBytes("utf-8"));
            return new String(encodeBytes);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    public static String decode(String content){
        try {
            byte[] decodeBytes = Base64.getDecoder().decode(content.getBytes("utf-8"));
            return new String(decodeBytes);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


}
