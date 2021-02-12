package com.example.system.service;

import com.example.system.model.po.SysUserPo;
import com.example.system.model.qo.LoginUserQo;
import com.example.system.model.vo.LoginUserVo;
import com.example.system.util.ResponseResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
public interface LoginService {

    ResponseResult<LoginUserVo> login(LoginUserQo loginUserQo) throws Exception;

    ResponseResult checkUserIsEffective(SysUserPo sysUserPo);

    ResponseResult logout(HttpServletRequest request);

}
