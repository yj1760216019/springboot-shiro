package com.carlinx.shiro.utils;

import java.security.MessageDigest;

public class MD5Util {

    public static String encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            str = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return str;
    }

    /**
     * 带盐值加密
     *
     * @param str
     *            待加密字符串
     * @param salt
     *            盐值
     */
    public static String encode(String str, String salt) {

        return encode(str + salt);
    }


}
