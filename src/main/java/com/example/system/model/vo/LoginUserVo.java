package com.example.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 在线用户信息
 * @author 老骨头（lgt）
 */
@Data
public class LoginUserVo {

    /**
     * 登录账号
     */
    private String username;

    /**
     * 登录角色
     */
    private String roleName;

    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;

    /**
     * 登录token
     */
    private String token;
}
