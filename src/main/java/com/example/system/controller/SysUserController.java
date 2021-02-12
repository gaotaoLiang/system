package com.example.system.controller;

import com.example.system.model.suo.SysUserSuo;
import com.example.system.service.SysUserService;
import com.example.system.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "[用户管理]")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public ResponseResult register(@ApiParam(value = "注册参数", required = true) @RequestBody SysUserSuo sysUserSuo){
        return sysUserService.register(sysUserSuo);
    }

}
