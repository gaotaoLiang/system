package com.siec.system.controller;

import com.siec.system.model.qo.LoginUserQo;
import com.siec.system.model.vo.LoginUserVo;
import com.siec.system.service.LoginService;
import com.siec.system.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Api(tags = "[权限]-用户登录、退出")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoginService loginService;

    /**
     * 用户账号密码登录
     * username：用户名
     * password：密码
     *
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public ResponseResult<LoginUserVo> login(@ApiParam(value = "登录参数", required = true) @RequestBody LoginUserQo loginUserQo) throws Exception {
        return loginService.login(loginUserQo);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public ResponseResult logout(HttpServletRequest request) {
        return loginService.logout(request);
    }

}
