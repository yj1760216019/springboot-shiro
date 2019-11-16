package com.carlinx.shiro.controller;

import com.carlinx.shiro.base.result.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Api("shiro测试")
public class TestController {

    @GetMapping("/shiro")
    @ApiOperation("shiro测试")
    @RequiresAuthentication
    public JsonResult test(){
        return JsonResult.success("你好 世界");
    }

}
