package com.demo.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.system.model.po.AppProjectPersonalInfoPo;

public interface AppProjectPersonalInfoMapper extends BaseMapper<AppProjectPersonalInfoMapper> {
    int deleteByPrimaryKey(Integer id);

    int insert(AppProjectPersonalInfoPo record);

    int insertSelective(AppProjectPersonalInfoPo record);

    AppProjectPersonalInfoPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppProjectPersonalInfoPo record);

    int updateByPrimaryKey(AppProjectPersonalInfoPo record);
}