package com.demo.system.model.po;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sys_role")
public class SysRolePo {
    private Integer rId;

    private String roleName;

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}