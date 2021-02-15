package com.siec.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siec.system.model.po.SysUserPo;
import com.siec.system.model.suo.SysUserSuo;
import com.siec.system.util.ResponseResult;

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
