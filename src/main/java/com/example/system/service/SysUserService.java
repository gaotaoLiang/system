package com.example.system.service;

import com.example.system.model.po.SysUserPo;
import com.example.system.model.suo.SysUserSuo;
import com.example.system.util.ResponseResult;

import java.util.Set;

public interface SysUserService {

    Set<String> getUserRolesSet(String username);

    SysUserPo selectUserByUsername(String username);

    SysUserPo selectUserByPhone(String phone);

    SysUserPo selectUserByEmail(String email);

    ResponseResult register(SysUserSuo sysUserSuo);
}
