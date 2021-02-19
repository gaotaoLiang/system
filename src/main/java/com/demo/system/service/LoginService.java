package com.demo.system.service;

import com.demo.system.model.po.SysUserPo;
import com.demo.system.model.qo.LoginUserQo;
import com.demo.system.model.vo.LoginUserVo;
import com.demo.system.util.ResponseResult;

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
