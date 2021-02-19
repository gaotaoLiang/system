package com.demo.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.system.model.po.SysUserPo;
import com.demo.system.model.suo.SysUserSuo;
import com.demo.system.util.ResponseResult;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface SysUserService {

    Set<String> getUserRolesSet(String username);

    SysUserPo selectUserByUsername(String username);

    SysUserPo selectUserByPhone(String phone);

    SysUserPo selectUserByEmail(String email);

    ResponseResult register(SysUserSuo sysUserSuo) throws ExecutionException;

    ResponseResult update(SysUserSuo sysUserSuo);

    Page<SysUserPo> selectAllUserByPage(Integer pageNumber, Integer pageSize );

    ResponseResult getEmailCode(String email, String username);

}
