package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.entity.SysUser;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component("sysUserMapper")
public interface SysUserMapper extends BaseMapper<SysUser> {

    Set<String> getUserRolesSet(String username);

    SysUser selectUserByUsername(String username);

    SysUser selectUserByPhone(String phone);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}

