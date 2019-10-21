package com.carlinx.shiro.utils;

import com.sun.crypto.provider.SunJCE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

@Component
public class AesUtil {
    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);
    @Value("${encrypt-aes-key}")
    private static String encryptAESKey = "V2FuZzkyNuYSKIuwqTQkFQSUpXVA";

    /**
     * 加密
     * @param content
     * @return
     */
    public static String encode(String content) {
        try {
            Security.addProvider(new SunJCE());
            //实例化AES算法秘钥生成器
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            //初始化随机数
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(Base64Util.decode(encryptAESKey).getBytes());
            aes.init(128, secureRandom);
            //初始化 SecreKey 负责生成秘钥
            SecretKey secretKey = aes.generateKey();
            //初始化 Cipher  负责加密或解密工作
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] contentBytes = content.getBytes();
            //生成二进制加密结果
            byte[] finalBytes = cipher.doFinal(contentBytes);
            return Base64Util.encode(HexUtil.parseByte2HexStr(finalBytes));
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("getInstance()方法异常:" + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("初始化Cipher对象异常:" + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("加密异常，密钥有误:" + e.getMessage());
        } catch (BadPaddingException e) {
            throw new RuntimeException("加密异常，密钥有误:" + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 解密
     * @param content
     * @return
     */
    public static String decode(String content){
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            //实例化AES算法秘钥生成器
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            //初始化随机数
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(Base64Util.decode(encryptAESKey).getBytes());
            aes.init(128, secureRandom);
            // 初始化SecretKey 负责生成密钥
            SecretKey secretKey = aes.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            String base64 = Base64Util.decode(content);
            byte[] cipherByte = cipher.doFinal(HexUtil.parseHexStr2Byte(base64));
            return new String(cipherByte);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("getInstance()方法异常:" + e.getMessage());
        }  catch (NoSuchPaddingException e) {
            throw new RuntimeException("getInstance()方法异常:" + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("初始化Cipher对象异常:" + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("解密异常，密钥有误:" + e.getMessage());
        } catch (BadPaddingException e) {
            throw new RuntimeException("解密异常，密钥有误:" + e.getMessage());
        }
    }
}
