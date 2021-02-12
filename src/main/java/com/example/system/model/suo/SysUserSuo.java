package com.example.system.model.suo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
@Data
@ApiModel(value = "用户注册信息")
public class SysUserSuo {

    @ApiModelProperty(value = "用户名", example = "superadmin")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "用户注册邮箱", example = "123456789@qq.com")
    private String eMail;

    @ApiModelProperty(value = "用户注册手机号", example = "123456789")
    private String phone;

    @ApiModelProperty(value = "用户注册邮箱验证码", example = "123456")
    private String emailCode;

    @ApiModelProperty(value = "密码加密盐", example = "hrehseryh")
    private String salt;

}
