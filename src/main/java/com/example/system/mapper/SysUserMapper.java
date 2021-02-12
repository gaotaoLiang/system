package com.example.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.system.model.po.SysUserPo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserPo> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserPo record);

    int insertSelective(SysUserPo record);

    SysUserPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserPo record);

    int updateByPrimaryKey(SysUserPo record);
}