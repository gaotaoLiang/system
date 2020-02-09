package com.example.system.entity;

import lombok.Data;

/**
 * 在线用户信息
 * @author 老骨头（lgt）
 */
@Data
public class LoginUser {

    /**
     * 登录人名字
     */
    private String name;

    /**
     * 登录人账号
     */
    private String username;


    /**
     * 电子邮件
     */
    private String email;



    /**
     * 电话
     */
    private String phone;

    /**
     * 状态(1：正常 0：冻结 ）
     */
    private Integer status;


    private String token;
}
