package com.example.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.system.entity.LoginUser;
import com.example.system.entity.SysUser;
import com.example.system.exception.MyException;
import com.example.system.mapper.SysUserMapper;
import com.example.system.modules.jwt.JwtUtil;
import com.example.system.modules.redis.RedisUtil;
import com.example.system.service.SysUserService;
import com.example.system.util.*;
import com.example.system.util.RequestHeader;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户账号密码登录
     * username：用户名
     * password：密码
     *
     * @return
     */
    @PostMapping("/login")
    public Object login(@RequestBody JSONObject jsonObject) {

        Map<String, Object> map = new HashMap<>();
        map.put("username", jsonObject.getString("username"));
        map.put("password", jsonObject.getString("password"));
        SysUser sysUser = null;
        try {
            sysUser = sysUserMapper.selectByMap(map).get(0);
        } catch (Exception e) {
            throw new MyException("账号或密码错误，请确认后输入");
        }


        /**校验用户的有效性*/
        Result<JSONObject> result = (Result<JSONObject>) sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        /**获取登录者信息，以及生成token等操作*/
        LoginUser loginUser = userInfo(sysUser);

        /**返回登录者信息给前台*/
        if (loginUser != null) {
            logger.info("登录成功：" + jsonObject.getString("username"));
            return new ResponseResult(loginUser);
        }
        return new Result<>().error(500, "登录失败！");

    }

    /**
     * 获取登录短信验证码接口
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/sms")
    public Result<JSONObject> sms(@RequestBody JSONObject jsonObject) {
        Result<JSONObject> result = new Result();
        String phone = jsonObject.get("phone").toString();    //手机号
        //String smsmode=jsonObject.get("smsmode").toString();

        /**通过手机号校验用户有效性在发送验证码*/
        SysUser sysUser = sysUserService.selectUserByPhone(phone);
        if (sysUser == null) {
            return result.error(500, "用户手机号不存在，请确认后再输入");
        }

        //随机数
        String captcha = RandomUtil.randomNumber(6);

        try{
            boolean b = false;
            b = SmsHelper.sendSms(phone, captcha, SmsHelper.LOGIN_TEMPLATE_CODE);
            if (b == false) {
                return result.error(500, "短信发送失败！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return  result.error(500," 短信接口异常，请联系管理员！");
        }
        //验证码5分钟内有效
        redisUtil.set(phone, captcha, CommonConstant.SMS_OVER_TIME);

        return result.success("验证码已发送，请在五分钟内进行操作！");
    }

    /**
     * 手机号验证码登录接口
     *
     * @param jsonObject
     * @return
     */
    @PostMapping("/phoneLogin")
    public Object phoneLogin(@RequestBody JSONObject jsonObject) {

        Result<JSONObject> result = new Result<>();

        String phone = jsonObject.getString("mobile");   //手机号

        /**通过手机号校验用户有效性*/
        SysUser sysUser = sysUserService.selectUserByPhone(phone);
        if (sysUser == null) {
            return result.error(500, "用户手机号不存在，请确认后再输入");
        }


        String smscode = jsonObject.getString("captcha");  // 验证码
        Object code = redisUtil.get(phone);
        if (!smscode.equals(code)) {
            result.setMessages("手机验证码错误");
            return result;
        }

        /**获取登录者信息，以及生成token等操作*/
        LoginUser loginUser = userInfo(sysUser);
        //添加日志
        //sysBaseAPI.addLog("用户名: " + sysUser.getUsername() + ",登录成功！", CommonConstant.LOG_TYPE_1, null);
        /**返回登录者信息给前台*/
        if (loginUser != null) {
            logger.info("登录成功：" + jsonObject.getString("username"));
            return new ResponseResult(loginUser);
        }
        return new Result<>().error(500, "登录失败！");
    }

    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        Subject subject = SecurityUtils.getSubject();
        LoginUser sysUser = (LoginUser) subject.getPrincipal();
        logger.info(" 用户名:  " + sysUser.getUsername() + ",退出成功！ ");
        subject.logout();

        String token = request.getHeader(RequestHeader.LOGIN_SIGN);
        //清空用户Token缓存
        redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);   //CommonConstant.PREFIX_USER_TOKEN = PREFIX_USER_TOKEN_
        //清空用户权限缓存：权限Perms和角色集合
//        redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_ROLE + sysUser.getUsername());
//        redisUtil.del(CommonConstant.LOGIN_USER_CACHERULES_PERMISSION + sysUser.getUsername());
        return new Result<>().success("退出登录成功！");

    }

    /**
     * 获取登录者信息
     *
     * @param sysUser
     * @return
     */
    private LoginUser userInfo(SysUser sysUser) {
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();

        LoginUser loginUser = new LoginUser();
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
