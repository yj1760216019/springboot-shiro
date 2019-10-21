package com.carlinx.shiro;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.carlinx.shiro.utils.Base64Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShiroApplicationTests {

   @Test
   public void test() throws UnsupportedEncodingException {
      byte[] bytes = "中国".getBytes("utf-8");

      StringBuffer result = new StringBuffer();
      for (int i = 0; i < bytes.length; i++) {
         result.append(Long.toString(bytes[i] & 0xff, 2) + ",");
      }
      System.out.println(result.toString().substring(0, result.length() - 1));
      System.out.println(HexUtil.encodeHexStr(bytes));

   }

}
