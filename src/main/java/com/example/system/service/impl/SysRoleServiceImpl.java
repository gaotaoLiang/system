package com.example.system.service.impl;

import com.example.system.mapper.SysRoleMapper;
import com.example.system.model.po.SysRolePo;
import com.example.system.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> selectRoleByUserName(String username) {
        List<SysRolePo> sysRolePos = sysRoleMapper.selectRoleByUserName(username);
        List<String> roleNames = sysRolePos.stream().map(SysRolePo::getRoleName).collect(Collectors.toList());
        return roleNames;
    }
}
