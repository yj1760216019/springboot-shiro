package com.carlinx.shiro.filter;

import com.carlinx.shiro.base.result.JsonResult;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Executor;

@ControllerAdvice
public class MyExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);


    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public JsonResult UnauthenticatedException(HttpServletRequest request, Exception e){
        return JsonResult.fail("未登录");
    }



    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public JsonResult UnauthorizedException(HttpServletRequest request,Exception e){
        return JsonResult.fail("无权限");
    }




    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult GlobalExceptionHandler(HttpServletRequest request, Exception e){
        return JsonResult.fail(e.getMessage());
    }




}
