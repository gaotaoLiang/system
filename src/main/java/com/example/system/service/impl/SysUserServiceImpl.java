package com.example.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.system.entity.SysUser;
import com.example.system.mapper.SysRoleMapper;
import com.example.system.mapper.SysUserMapper;
import com.example.system.mapper.SysUserRoleMapper;
import com.example.system.modules.redis.RedisUtil;
import com.example.system.service.SysUserService;
import com.example.system.util.CommonConstant;
import com.example.system.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取用户角色集合
     * @param username
     * @return
     */
    @Override
    //@Cacheable(value = CacheConstant.LOGIN_USER_RULES_CACHE,key = "'Roles_'+#username")
    public Set<String> getUserRolesSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = sysUserRoleMapper.getRoleByUserName(username);
        //System.out.println(roles);
        logger.info("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }


    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    @Override
    public SysUser selectUserByUsername(String username) {
        return sysUserMapper.selectUserByUsername(username);
    }

    /**
     * 通过用户手机号查找用户
     * @param phone
     * @return
     */
    @Override
    public SysUser selectUserByPhone(String phone) {
        return sysUserMapper.selectUserByPhone(phone);
    }

    /**
     * 校验用户的有效性
     */
    @Override
    public Result<?> checkUserIsEffective(SysUser sysUser) {
        Result<?> result = new Result();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUser == null) {
            result.error(500,"账号或密码错误，请确认后再输入");
            return result;
        }
        //情况2：根据用户信息查询，该用户已冻结
        if (CommonConstant.USER_FREEZE.equals(sysUser.getStatus())) {
            result.error(500,"该用户已冻结");
            return result;
        }
        return result;
    }





}
