package com.example.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.system.common.constant.CommonConstant;
import com.example.system.common.constant.ResultStatus;
import com.example.system.common.exception.UnexpectedStatusException;
import com.example.system.mapper.SysUserMapper;
import com.example.system.model.po.SysUserPo;
import com.example.system.model.qo.LoginUserQo;
import com.example.system.model.vo.LoginUserVo;
import com.example.system.modules.jwt.JwtUtil;
import com.example.system.modules.redis.RedisUtil;
import com.example.system.service.LoginService;
import com.example.system.util.AesEncryptUtil;
import com.example.system.util.RequestHeader;
import com.example.system.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登录
     */
    @Override
    public ResponseResult<LoginUserVo> login(LoginUserQo loginUserQo) throws Exception {

        String username = loginUserQo.getUsername();
        //1. 校验用户是否有效
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        SysUserPo sysUserPo = sysUserMapper.selectOne(queryWrapper);
        ResponseResult responseResult = loginService.checkUserIsEffective(sysUserPo);
        if(!responseResult.isSuccess()) {
            return responseResult;
        }

        //步骤1：TODO 前端密码加密，后端进行密码解密，防止传输密码篡改等问题，不配就直接提示密码错误，并记录日志后期进行统计分析是否锁定
       // String password = PasswordUtil.encrypt(loginUserQo.getPassword(), CommonConstant.SECRET_KEY_PASSWORD, sysUserPo.getSalt());
        String password = AesEncryptUtil.desEncrypt(loginUserQo.getPassword()).trim(); //密码解密

        //2. 校验用户名或密码是否正确
        //String userPassword = PasswordUtil.encrypt(password, CommonConstant.SECRET_KEY_PASSWORD, sysUserPo.getSalt());
        String userPassword = AesEncryptUtil.encrypt(password);
        String sysPassword = sysUserPo.getPassword();
        if (!sysPassword.equals(userPassword)) {
            return ResponseResult.error(ResultStatus.USER_LOGIN_ERROR);
        }

        /**获取登录者信息，以及生成token等操作*/
        LoginUserVo loginUserVo = userInfo(sysUserPo);

        /**返回登录者信息给前台*/
        if (loginUserVo != null) {
            log.info("登录成功：{}", username);
            return  ResponseResult.success(loginUserVo);
        }
        return ResponseResult.error(ResultStatus.USER_LOGIN_FAILURE);
    }


    /**
     * 校验用户的有效性
     */
    @Override
    public ResponseResult checkUserIsEffective(SysUserPo sysUserPo) {
        ResponseResult<?> responseResult = new ResponseResult();
        //情况1：根据用户信息查询，该用户不存在
        if (sysUserPo == null) {
            responseResult.error(ResultStatus.USER_NOT_EXIST);
            return responseResult;
        }
        //情况2：根据用户信息查询，该用户已冻结
        if (CommonConstant.USER_FREEZE.equals(sysUserPo.getStatus())) {
            responseResult.error(ResultStatus.USER_FREEZE);
            return responseResult;
        }
        return responseResult;
    }

    /**
     * 退出
     */
    @Override
    public ResponseResult logout(HttpServletRequest request) {
        //用户退出逻辑
        try{
            Subject subject = SecurityUtils.getSubject();
            LoginUserVo sysUser = (LoginUserVo) subject.getPrincipal();
            log.info(" 用户名:  " + sysUser.getUsername() + ",退出成功！ ");
            subject.logout();

            String token = request.getHeader(RequestHeader.LOGIN_SIGN);
            //清空用户Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);   //CommonConstant.PREFIX_USER_TOKEN = PREFIX_USER_TOKEN_
            //清空用户权限缓存：权限Perms和角色集合
//        redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_ROLE + sysUser.getUsername());
//        redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_PERMISSION + sysUser.getUsername());
        }catch (Exception e){
            throw new UnexpectedStatusException(ResultStatus.USER_LOGOUT_FAILURE);
        }
        return ResponseResult.success(ResultStatus.USER_LOGOUT_SUCCESS);
    }

    /**
     * 获取登录者信息
     */
    private LoginUserVo userInfo(SysUserPo sysUser) {
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();

        LoginUserVo loginUser = new LoginUserVo();
        BeanUtils.copyProperties(sysUser, loginUser);    // 将A中的值赋给B，其中相同属性则处理，不相同属性则手动处理

        // 生成token
        String token = JwtUtil.sign(username, password);
        loginUser.setToken(token);

        //保存进redis
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        // 设置超时时间
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);

        return loginUser;
    }

}
