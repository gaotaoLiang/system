package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siec.system.common.constant.ResultStatus;
import com.siec.system.common.exception.UnexpectedStatusException;
import com.siec.system.mapper.SysUserMapper;
import com.siec.system.model.po.SysUserPo;
import com.siec.system.model.suo.SysUserSuo;
import com.siec.system.modules.redis.RedisUtil;
import com.siec.system.service.SysRoleService;
import com.siec.system.service.SysUserService;
import com.siec.system.util.AesEncryptUtil;
import com.siec.system.util.OConvertUtils;
import com.siec.system.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserPo> implements SysUserService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取用户角色集合
     */
    @Override
    //@Cacheable(value = CacheConstant.LOGIN_USER_RULES_CACHE,key = "'Roles_'+#username")
    public Set<String> getUserRolesSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = sysRoleService.selectRoleByUserName(username);
        log.info("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }

    /**
     * 通过用户名查找用户
     */
    @Override
    public SysUserPo selectUserByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        SysUserPo sysUserPo = sysUserMapper.selectOne(queryWrapper);
        return sysUserPo;
    }

    /**
     * 通过手机号查找用户
     */
    @Override
    public SysUserPo selectUserByPhone(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone", phone);
        SysUserPo sysUserPo = sysUserMapper.selectOne(queryWrapper);
        return sysUserPo;
    }

    /**
     * 通过邮箱查找用户
     */
    @Override
    public SysUserPo selectUserByEmail(String email) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("e_mail", email);
        SysUserPo sysUserPo = sysUserMapper.selectOne(queryWrapper);
        return sysUserPo;
    }

    /**
     * 用户注册
     */
    @Override
    public ResponseResult register(SysUserSuo sysUserSuo) {
        String username = sysUserSuo.getUsername();
        String password = sysUserSuo.getPassword();
        String phone = sysUserSuo.getPhone();
        String email = sysUserSuo.getEMail();

        SysUserPo sysUserPoUsername = selectUserByUsername(username);
        if (sysUserPoUsername != null) {
            return ResponseResult.error(ResultStatus.USER_REGISTERED);
        }

        SysUserPo sysUserPoPhone = selectUserByPhone(phone);
        if (sysUserPoPhone != null) {
            return ResponseResult.error(ResultStatus.USER_PHONE_REGISTERED);
        }

        SysUserPo sysUserPoEmail = selectUserByEmail(email);
        if (sysUserPoEmail != null) {
            return ResponseResult.error(ResultStatus.USER_EMAIL_REGISTERED);
        }

      /*  if (!smscode.equals(code)) {
            result.setMessage("手机验证码错误");
            result.setSuccess(false);
            return result;
        }*/

        SysUserPo sysUserPo = new SysUserPo();
        try {
            String salt = OConvertUtils.randomGen(8);
           // String passwordEncode = PasswordUtil.encrypt(password, CommonConstant.SECRET_KEY_PASSWORD, salt);
            String passwordEncode = AesEncryptUtil.encrypt(password);
            sysUserPo.setSalt(salt);
            sysUserPo.setUsername(username);
            sysUserPo.setPassword(passwordEncode);
            sysUserPo.seteMail(email);
            sysUserPo.setPhone(phone);
            sysUserPo.setStatus(String.valueOf(1));
            sysUserMapper.insertSelective(sysUserPo);
            log.info("用户注册成功：{}", username);
        } catch (Exception e) {
            log.info("用户注册失败：{}", e.getMessage(), e);
            throw new UnexpectedStatusException(ResultStatus.USER_REGISTER_FAILURE);
        }
        return ResponseResult.success(ResultStatus.USER_REGISTER_SUCCESS);
    }
}
