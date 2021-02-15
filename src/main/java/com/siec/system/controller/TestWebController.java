package com.siec.system.controller;

import com.siec.system.model.po.SysUserPo;
import com.siec.system.modules.redis.util.RedisUtil;
import com.siec.system.util.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * @Description: 当你的redis数据库里面本来存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，
 * 那么你就使用StringRedisTemplate即可，但是如果你的数据是复杂的对象类型，
 * 而取出的时候又不想做任何的数据转换，直接从Redis里面取出一个对象，
 * 那么使用RedisTemplate是更好的选择。

 * @author: 老骨头（lgt）
 * @date: 2019/9/20
 */
//@RestController
public class TestWebController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    private SysUserPo sysUser;

    /**
     * 测试redis时候添加数据成功
     * @return
     */
    @GetMapping("/save")
    public ResponseResult save() {

        /**保存字符串*/
        stringRedisTemplate.opsForValue().set("用户", "老骨头");
        //redisUtil = new RedisUtil();   //注入bean的类不能在实例化
        redisUtil.set("角色", "学生");

        /**保存对象*/
        sysUser = new SysUserPo();
        //sysUser.setName("hahah");
        sysUser.setUsername("123456");
        sysUser.setPassword("123456");
        redisUtil.set("hahaha", sysUser);

        ValueOperations<String, Object> value = redisTemplate.opsForValue();

        return new ResponseResult(value.get("hahaha"));
    }

    @GetMapping("/delete")
    public ResponseResult delete() {
        redisUtil.del("角色");
        ValueOperations<String,Object> value = redisTemplate.opsForValue();

        return new ResponseResult(value.get("角色"));

    }


    /**
     * 测试用户角色是否验证成功
     *
     * @return
     */
    @GetMapping("/test")
    @RequiresRoles({"admin"})
    public String test() {
        return "测试拦截能否进来";
    }

}
