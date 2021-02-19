package com.demo.system.model.suo;

import com.demo.system.model.po.AppProjectPersonalInfoPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/12
 */
@Data
@ApiModel(value = "项目申请信息")
public class AppProjectSuo extends AppProjectPersonalInfoPo {

    @ApiModelProperty(value = "id", example = "1")
    private Integer id;

    @ApiModelProperty(value = "标题", example = "xx系统项目")
    private String title;

    @ApiModelProperty(value = "众筹金额", example = "10000")
    private Long amount;

    @ApiModelProperty(value = "项目概要", example = "项目用途、统计数据、预期结果等等")
    private String summary;

    @ApiModelProperty(value = "项目计划书保存路径", example = "xxx")
    private String planPath;

}
