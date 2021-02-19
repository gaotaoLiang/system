package com.demo.system.model.bo;

import lombok.Data;

import java.util.Date;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/12
 */
@Data
public class AppProjectBo {

    private Integer id;

    private String title;

    private String amount;

    private String summary;

    private Integer personalInfoId;

    private String planPath;

    private String approvalStatus;

    private Date createTime;

    private Date updateTime;
}
