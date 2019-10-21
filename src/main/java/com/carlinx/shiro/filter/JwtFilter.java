package com.carlinx.shiro.filter;

import cn.hutool.json.JSONUtil;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.carlinx.shiro.base.token.JwtToken;
import com.carlinx.shiro.config.constant.JwtConstant;
import com.carlinx.shiro.config.constant.RedisConstant;
import com.carlinx.shiro.utils.JwtUtil;
import com.carlinx.shiro.utils.cache.RedisOperator;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Value("${refresh-token-expire-time}")
    private String refreshTokenExpireTime = "1800";
    @Value("${access-token-expire-time}")
    private String accessTokenExpireTime = "1200";

    @Autowired
    private RedisOperator redisOperator;


    /**
     * 未传Authorization放行 因为应用中可能有游客模式
     * 这样可以在controller中对需求进行疏导 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些方法只能登录用户才能访问  只需要加@RequiresAuthentication注解
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //传token进行登录验证   未传token放行
        if(isLoginAttempt(request,response)){
           try {
               //登录
               executeLogin(request,response);
               //登录成功 刷新缓存时间
               String accessToken = this.getAuthzHeader(request);
               String userId = JwtUtil.getClaim(accessToken, JwtConstant.PRIMART_KEY);
               redisOperator.expire(RedisConstant.PREFIX_SHIRO_ACCESS_TOKEN + userId,Long.parseLong(accessTokenExpireTime));
           }catch (Exception e){
               //获取异常错误信息
               String errroeMessage = e.getMessage();
               //获取异常应用
               Throwable throwable = e.getCause();
               if(throwable != null && throwable instanceof SignatureVerificationException){
                   errroeMessage = "tooken或秘钥不正确"+throwable.getMessage();
               }else if(throwable != null && throwable instanceof TokenExpiredException){
                   errroeMessage = "token已过期";
               }
               if(throwable != null){
                   errroeMessage = throwable.getMessage();
               }
               this.ExceptionResponse(request,response,errroeMessage);
               return false;
           }
        }
        return true;
    }


    /**
     * 重写去除executeLogin方法
     * 否则会循环调用doGetAuthenticationInfo方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }


    /**
     * 验证是否是登录意图
     * 无Authorization返回false代表登录
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 拿到当前Header中的token
        String token = this.getAuthzHeader(request);
        return token != null;
    }


    /**
     * 进行登录认证
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // 请求头中获取token
        JwtToken token = new JwtToken(this.getAuthzHeader(request));
        // 进行登录认证   抛异常登录失败
        this.getSubject(request, response).login(token);
        return true;
    }



    /**
     * 通用异常响应结果
     * @param request
     * @param response
     * @param message
     */
    public void ExceptionResponse(ServletRequest request,ServletResponse response,String message){
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            Map map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",message);
            map.put("data",null);
            String result = JSONUtil.toJsonStr(map);
            printWriter.append(result);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }finally {
            if(printWriter != null){
                printWriter.close();
            }
        }

    }




    /**
     * 跨域支持
     * @param request
     * @param response
     * @return
     */
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        //跨域OPTIONS请求  直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request,response);
    }


}
