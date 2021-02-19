package com.demo.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.system.mapper.SysUserMapper;
import com.demo.system.model.po.SysUserPo;
import com.demo.system.model.suo.SysUserSuo;
import com.demo.system.service.SysUserService;
import com.demo.system.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

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

    @Autowired
    private SysUserMapper sysUserMapper;

    @PostMapping(value = "/user")
    @ApiOperation(value = "用户注册")
    public ResponseResult register(@ApiParam(value = "注册参数", required = true) @RequestBody SysUserSuo sysUserSuo) throws ExecutionException {
        return sysUserService.register(sysUserSuo);
    }

    @PutMapping(value = "/user")
    @ApiOperation(value = "更新用户信息（密码、邮箱、手机号、用户状态）")
    public ResponseResult updateUser(@ApiParam(value = "用户更新信息参数") @RequestBody SysUserSuo sysUserSuo) {
       return sysUserService.update(sysUserSuo);
    }

    @GetMapping(value = "/users")
    @ApiOperation(value = "查找所有的用户(分页)")
    public ResponseResult selectAll(@ApiParam(value = "页号", required = true) @RequestParam("pageNumber") Integer pageNumber,
                                    @ApiParam(value = "页大小", required = true) @RequestParam("pageNumber") Integer pageSize) {
        Page<SysUserPo> page = sysUserService.selectAllUserByPage(pageNumber, pageSize);
        return ResponseResult.success(page);
    }

    @PutMapping(value = "/user/{id}")
    @ApiOperation(value = "删除用户信息")
    public ResponseResult deleteByUser(@ApiParam(value = "id", required = true) @RequestParam("id") Integer id) {
        sysUserMapper.deleteByPrimaryKey(id);
        return ResponseResult.success();
    }

    @GetMapping(value = "/user/code")
    @ApiOperation(value = "获取邮箱验证码")
    public ResponseResult getEmailCode(@ApiParam(value = "邮箱", required = true) @RequestParam("email") String email,
                                       @ApiParam(value = "用户名", required = true) @RequestParam("username") String username){
        return sysUserService.getEmailCode(email, username);
    }


}
