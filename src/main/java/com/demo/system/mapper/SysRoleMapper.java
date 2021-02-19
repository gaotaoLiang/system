package com.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.system.model.po.SysRolePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRolePo> {
    int deleteByPrimaryKey(Integer rId);

    int insert(SysRolePo record);

    int insertSelective(SysRolePo record);

    SysRolePo selectByPrimaryKey(Integer rId);

    int updateByPrimaryKeySelective(SysRolePo record);

    int updateByPrimaryKey(SysRolePo record);

    List<SysRolePo> selectRoleByUserName(@Param("username") String username);
}