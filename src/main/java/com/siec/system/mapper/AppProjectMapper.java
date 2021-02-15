package com.siec.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.siec.system.model.po.AppProjectPo;

public interface AppProjectMapper extends BaseMapper<AppProjectPo> {
    int deleteByPrimaryKey(Integer id);

    int insert(AppProjectPo record);

    int insertSelective(AppProjectPo record);

    AppProjectPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppProjectPo record);

    int updateByPrimaryKey(AppProjectPo record);
}