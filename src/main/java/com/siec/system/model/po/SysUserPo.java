package com.siec.system.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("sys_user")
@Data
public class SysUserPo {

   // @TableId(type= IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String rId;

    private String eMail;

    private String phone;

    private String status;

    private String salt;

    private Date registerTime;

    private Date updateTime;

    public SysUserPo() {
    }

    public SysUserPo(Integer id, String username, String password, String rId, String eMail, String phone, String status, String salt, Date registerTime, Date updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rId = rId;
        this.eMail = eMail;
        this.phone = phone;
        this.status = status;
        this.salt = salt;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
    }
}