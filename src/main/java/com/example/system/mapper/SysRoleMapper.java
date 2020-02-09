package com.example.system.mapper;

import com.example.system.entity.SysRole;
import org.springframework.stereotype.Component;

@Component("sysRoleMapper")
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}