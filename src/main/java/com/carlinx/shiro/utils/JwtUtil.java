package com.carlinx.shiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.carlinx.shiro.config.constant.JwtConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    //accessToken过期时间
    @Value("${access-token-expire-time}")
    private static String accessTokenExpireTime = "1200";

    //Jwt认证加密私钥(Base64加密)
    @Value("${encrypt-jwt-key}")
    private static String encryptJWTKey = "U0JBUElOENhspJrzkyNjQ1NA";



    /**
     * 校验token是否正确
     * @param token
     * @return
     */
    public static Boolean verifyToken(String token){
        try {
            //账号+jwt私钥解密
            String secret = getClaim(token, JwtConstant.PRIMART_KEY)+Base64Util.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT verifyJWT = verifier.verify(token);
            return true;
        }catch (Exception e){
            throw new RuntimeException("token不正确，token认证解密失败");
        }
    }





    /**
     * 生成jwt  Token
     * @param key
     * @param currentTimeMillis
     * @return
     */
    public static String signToken(String key,String currentTimeMillis){
        try {
            //账号加JWT私钥加密
            String secret =  key + Base64Util.decode(encryptJWTKey);
            //设置过期时间   单位毫秒  所以乘1000
            Date expireDate = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //生成token
            return JWT.create().withClaim(JwtConstant.PRIMART_KEY,key)
                    .withClaim(JwtConstant.CURRENT_TIME_MILLIS,currentTimeMillis)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        }catch (Exception e){
            throw new RuntimeException("加密token失败");
        }
    }



    /**
     * 获取token中公共的信息
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token,String claim){
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(claim).asString();
        }catch (JWTDecodeException e){
            throw new RuntimeException("token不正确，解密token中的公共信息出错");
        }catch (Exception e){
            throw new RuntimeException("token不正确，解密token失败");
        }
    }
}
