package com.siec.system.model.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/12
 */
@Data
@TableName("sys_user")
public class SysUserVo {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "用户名", example = "superadmin")
    private String username;

    @ApiModelProperty(value = "用户角色", example = "系统管理员：1，普通用户：2")
    private String rId;

    @ApiModelProperty(value = "用户邮箱", example = "123456789@qq.com")
    private String eMail;

    @ApiModelProperty(value = "用户手机号", example = "123456789")
    private String phone;

    @ApiModelProperty(value = "用户状态", example = "1")
    private String status;

    @ApiModelProperty(value = "用户创建时间", example = "2021-02-12 16:50:25")
    private Date registerTime;

    @ApiModelProperty(value = "用户更新时间", example = "2021-02-12 16:50:25")
    private Date updateTime;
}
