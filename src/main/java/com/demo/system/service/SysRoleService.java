package com.demo.system.service;

import java.util.List;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
public interface SysRoleService {

    List<String> selectRoleByUserName(String username);
}
