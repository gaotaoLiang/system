package com.demo.system.model.suo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
@Data
@ApiModel(value = "用户注册或更新信息", description = "在更新用户信息时，id传参则表示管理员根据数据表id修改用户信息，id传参为空则表示用户自更新信息")
public class SysUserSuo {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "用户名", example = "superadmin")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    @ApiModelProperty(value = "用户邮箱", example = "123456789@qq.com")
    private String eMail;

    @ApiModelProperty(value = "用户手机号", example = "123456789")
    private String phone;

    @ApiModelProperty(value = "用户状态", example = "1")
    private String status;

    @ApiModelProperty(value = "邮箱验证码", example = "123456")
    private String emailCode;

}
