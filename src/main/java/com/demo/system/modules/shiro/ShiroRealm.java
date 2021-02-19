package com.demo.system.modules.shiro;


import com.demo.system.common.constant.CommonConstant;
import com.demo.system.mapper.SysRoleMapper;
import com.demo.system.model.po.SysRolePo;
import com.demo.system.model.po.SysUserPo;
import com.demo.system.model.vo.LoginUserVo;
import com.demo.system.modules.jwt.JwtToken;
import com.demo.system.modules.jwt.JwtUtil;
import com.demo.system.modules.redis.util.RedisUtil;
import com.demo.system.service.SysUserService;
import com.demo.system.util.OConvertUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 自定义Realm
 *
 * @author 老骨头
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 使用JWT替代原生Token, 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     * 需要权限认证的时候才会调用
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("————权限认证 [ roles、permissions]————");
        LoginUserVo loginUser = null;
        String username = null;
        if (principalCollection != null) {
            /**
             * 获取用户名称
             * principalCollection.getPrimaryPrincipal()获取到的内容为认证方法中SimpleAuthenticationInfo赋予的第一个值
             * 这里在SimpleAuthenticationInfo中第一个内容为用户名和token的混合字符串
             */
            loginUser = (LoginUserVo) principalCollection.getPrimaryPrincipal();
            username = loginUser.getUsername();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = sysUserService.getUserRolesSet(username);
        info.setRoles(roleSet);

        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
       /* Set<String> permissionSet = sysUserService.getUserPermissionsSet(username);
        info.addStringPermissions(permissionSet);*/
        return info;
    }

    /**
     * 认证
     * doGetAuthenticationInfo在认证身份的时候就会进去
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String token = (String) authenticationToken.getCredentials();   // 获取用户的用户名和token

        String username = null;

        try {
            // 解密获得username，用于和数据库进行对比
            username = JwtUtil.getUsername(token);
        } catch (Exception e) {
            throw new AuthenticationException("token错误或值为空");
        }
        if (username == null) {
            logger.error("token无效(空''或者null都不行!)");
            throw new AuthenticationException("token无效");
        }
        /***
         * 通过用户填写的用户名从数据库查询出User信息及用户关联的角色，权限信息，以备权限分配时使用
         */
        SysUserPo sysUser = sysUserService.selectUserByUsername(username);
        if (sysUser == null) {
            logger.error("用户不存在!)");
            throw new UnknownAccountException("用户不存在");
        }

        /**
         * 校验token是否超时失效 & 或者账号密码是否错误
         */
        if (!jwtTokenRefresh(token, username, sysUser.getPassword())) {
            logger.error("token超时失效或者账号密码错误");
            throw new AuthenticationException("Token失效，请重新登录!!!");
        }

        // 判断用户状态
        if (Integer.parseInt(sysUser.getStatus()) != 1) {
            throw new AuthenticationException("账号已被锁定,请联系管理员!");
        }

        LoginUserVo loginUser = new LoginUserVo();
        BeanUtils.copyProperties(sysUser, loginUser);    // 将A中的值赋给B，其中相同属性则处理，不相同属性则手动处理
       List<SysRolePo>  sysRolePos = sysRoleMapper.selectRoleByUserName(username);
        loginUser.setRoleName(sysRolePos.get(0).getRoleName());


        /**
         * SimpleAuthenticationInfo赋予什么内容，授权的时候获取到的就是什么内容
         * 通过配置中的 HashedCredentialsMatcher（如果有写） 进行自动对密码进行加密校验，写法如下
         * return new SimpleAuthenticationInfo(user, user.getPassword(),
         * ByteSource.Util.bytes(user.getSalt()), getName());
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                loginUser,
                token,
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * JWTToken刷新生命周期 （解决用户一直在线操作，提供Token失效问题）
     * 1、登录成功后将用户的JWT生成的Token作为k、v存储到cache缓存里面(这时候k、v值一样)
     * 2、当该用户再次请求时，通过JWTFilter层层校验之后会进入到doGetAuthenticationInfo进行身份验证
     * 3、当该用户这次请求JWTToken值还在生命周期内，则会通过重新PUT的方式k、v都为Token值，缓存中的token值生命周期时间重新计算(这时候k、v值一样)
     * 4、当该用户这次请求jwt生成的token值已经超时，但该token对应cache中的k还是存在，则表示该用户一直在操作只是JWT的token失效了，程序会给token对应的k映射的v值重新生成JWTToken并覆盖v值，该缓存生命周期重新计算
     * 5、当该用户这次请求jwt在生成的token值已经超时，并在cache中不存在对应的k，则表示该用户账户空闲超时，返回用户信息已失效，请重新登录。
     * 6、每次当返回为true情况下，都会给Response的Header中设置Authorization，该Authorization映射的v为cache对应的v值。
     * 7、注：当前端接收到Response的Header中的Authorization值会存储起来，作为以后请求token使用
     * 参考方案：https://blog.csdn.net/qq394829044/article/details/82763936
     *
     * @param userName
     * @param passWord
     * @return
     */
    public boolean jwtTokenRefresh(String token, String userName, String passWord) {
        String cacheToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        //先判断缓存的token是否为空
        if (OConvertUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.verify(token, userName, passWord)) {
                String newAuthorization = JwtUtil.sign(userName, passWord);
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                // 设置超时时间
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);
            } else {
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, cacheToken);
                // 设置超时时间
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME / 1000);
            }
            return true;
        }
        return false;
    }


}
