package com.example.system.service;

import com.example.system.entity.SysUser;
import com.example.system.util.Result;

import java.util.Set;

public interface SysUserService {

    Set<String> getUserRolesSet(String username);

    SysUser selectUserByUsername(String username);

    SysUser selectUserByPhone(String phone);

    Result<?> checkUserIsEffective(SysUser sysUser);

}
