package com.siec.system.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "项目申请者个人详细信息")
@TableName("app_project_personal_info")
public class AppProjectPersonalInfoPo {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "申请者姓名", example = "张三")
    private String name;

    @ApiModelProperty(value = "申请者年龄", example = "23")
    private Integer age;

    @ApiModelProperty(value = "申请者所在学校", example = "xx大学")
    private String school;

    @ApiModelProperty(value = "申请者手机号", example = "12345678999")
    private String phone;

    @ApiModelProperty(value = "申请者邮箱", example = "123456@qq.com")
    private String eMail;

    @ApiModelProperty(value = "申请者学生照照片保存路径", example = "xxx")
    private String studentCardPath;
}