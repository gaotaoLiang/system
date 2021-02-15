package com.siec.system.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
@Data
@ApiModel(value = "登录用户名和密码")
public class LoginUserQo {

    @ApiModelProperty(value = "用户名", example = "superadmin")
    private String username;

    @ApiModelProperty(value = "密码", example = "1F9Tyyc/LDvQzGrYEgF+bg==")
    private String password;

}
