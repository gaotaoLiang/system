package com.siec.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siec.system.common.constant.CommonConstant;
import com.siec.system.common.constant.ResultStatus;
import com.siec.system.common.exception.UnexpectedStatusException;
import com.siec.system.mapper.SysUserMapper;
import com.siec.system.model.po.SysUserPo;
import com.siec.system.model.suo.SysUserSuo;
import com.siec.system.modules.redis.util.RedisUtil;
import com.siec.system.service.SysRoleService;
import com.siec.system.service.SysUserService;
import com.siec.system.util.*;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserPo> implements SysUserService {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SendEmailUtil sendEmailUtil;

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
    public ResponseResult register(SysUserSuo sysUserSuo) throws ExecutionException {
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

       String emailCode = GuavaCacheUtil.getInstance().getCache(CommonConstant.USER_REGISTER_EMAIL_CODE_EMAIL + email);
        if (!sysUserSuo.getEmailCode().equals(emailCode)) {
            return ResponseResult.error(ResultStatus.EMAIL_CODE_IS_NOT_EXIST);
        }

        SysUserPo sysUserPo = new SysUserPo();
        try {
            String salt = OConvertUtils.randomGen(8);
            // String passwordEncode = PasswordUtil.encrypt(password, CommonConstant.SECRET_KEY_PASSWORD, salt);
            String passwordEncode = AesEncryptUtil.encrypt(password);
            sysUserPo.setSalt(salt);
            sysUserPo.setUsername(username);
            sysUserPo.setPassword(passwordEncode);
            sysUserPo.setEMail(email);
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

    @Override
    public ResponseResult update(SysUserSuo sysUserSuo) {
        try {
            //在更新用户信息时，id传参则表示管理员根据数据库表id修改用户信息，id传参为空则表示用户自更新信息
            if (ObjectUtils.isEmpty(sysUserSuo.getId())) {
                SysUserPo userPo = selectUserByUsername(sysUserSuo.getUsername());
                if (userPo == null) {
                    return ResponseResult.error(ResultStatus.USER_NOT_EXIST);
                }

                if (CommonConstant.USER_FREEZE.equals(userPo.getStatus())) {
                    return ResponseResult.error(ResultStatus.USER_FREEZE);
                }

                SysUserPo sysUserPo = new SysUserPo();
                BeanUtils.copyProperties(sysUserSuo, sysUserPo);
                sysUserPo.setUpdateTime(new Date());
                sysUserMapper.updateByUsernameSelective(sysUserPo);
            } else {
                SysUserPo sysUserPo = new SysUserPo();
                BeanUtils.copyProperties(sysUserSuo, sysUserPo);
                sysUserPo.setUpdateTime(new Date());
                sysUserMapper.updateByPrimaryKeySelective(sysUserPo);
            }
            return ResponseResult.success();
        } catch (Exception e) {
            log.info("user update message error: {}", e.getMessage());
            throw new UnexpectedStatusException(ResultStatus.FAILURE);
        }
    }

    @Override
    public Page<SysUserPo> selectAllUserByPage(Integer pageNumber, Integer pageSize) {
        try {
            Page<SysUserPo> page = new Page<>(pageNumber, pageSize);
            QueryWrapper<SysUserPo> queryWrapper = new QueryWrapper<>();  //2.x的EntityWrapper到3.x也变成了QueryWrapper
            Page<SysUserPo> sysUserPoIPage = (Page<SysUserPo>) sysUserMapper.selectPage(page, queryWrapper);
            List<SysUserPo> sysUserPos = sysUserPoIPage.getRecords();

            List<SysUserPo> sysUserPoList = sysUserPos.stream().map(sysUserPo -> new SysUserPo(sysUserPo.getId(),
                    sysUserPo.getUsername(), null, sysUserPo.getRId(), sysUserPo.getEMail(),
                    sysUserPo.getPhone(), sysUserPo.getStatus(), null, sysUserPo.getRegisterTime(),
                    sysUserPo.getUpdateTime())).collect(Collectors.toList());

            sysUserPoIPage.setRecords(sysUserPoList);
            return sysUserPoIPage;
        } catch (Exception e) {
            log.info("SysUserServiceImpl selectAllUserByPage error: {}", e.getMessage());
            throw new UnexpectedStatusException(ResultStatus.USER_PAGE_FAILURE);
        }
    }

    /**
     * username为空表示用户注册获取邮箱验证码, 非空表示用户找回密码获取邮箱验证码
     *
     * @param email
     * @param username
     * @return
     */
    @Override
    public ResponseResult getEmailCode(
            @RequestParam("mail") @ApiParam("邮箱") String email,
            @RequestParam("userId") @ApiParam("用户id") String username) {

        String codeStr = RandomUtil.randomNumber(6);   //生成6位随机验证码
        QueryWrapper queryWrapper = new QueryWrapper();
        SysUserPo sysUserPo;
        boolean result;   //邮箱验证码是否发送成功标记
        if (StringUtils.isEmpty(username)) {
            queryWrapper.eq("e_mail", email);
            sysUserPo = sysUserMapper.selectOne(queryWrapper);
            if (sysUserPo == null) {
                return ResponseResult.error(ResultStatus.EMAIL_IS_NOT_EXIST);
            }
            GuavaCacheUtil.getInstance().addCache(CommonConstant.USER_REGISTER_EMAIL_CODE_EMAIL + email, codeStr);   //将邮箱验证码缓存
            result = sendEmailUtil.sendEmailMessages(email, "学生互联网创业众筹邮箱验证码", "注册密码邮箱验证码：" + codeStr + "\n" + "请在十分钟内使用");
        } else {
            queryWrapper.eq("username", username);
            queryWrapper.eq("e_mail", email);
            sysUserPo = sysUserMapper.selectOne(queryWrapper);
            if (sysUserPo == null) {
                return ResponseResult.error(ResultStatus.EMAIL_OR_USERNAME_IS_NOT_EXIST);
            }
            GuavaCacheUtil.getInstance().addCache(CommonConstant.USER_FORGET_EMAIL_CODE_USERNAME + username, codeStr);   //将邮箱验证码缓存
            result = sendEmailUtil.sendEmailMessages(email, "学生互联网创业众筹邮箱验证码", "找回密码邮箱验证码：" + codeStr + "\n" + "请在十分钟内使用");
        }

        if (!result) {
            return ResponseResult.error(ResultStatus.EMAIL_CODE_SEND_FAILURE);
        }
        return ResponseResult.success(ResultStatus.EMAIL_CODE_SEND_SUCCESS);
    }
}
