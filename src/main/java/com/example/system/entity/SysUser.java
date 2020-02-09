package com.example.system.entity;

import java.io.Serializable;

/**
 * 记住实体要序列化一定要实现Serializable接口
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String college;

    private String labCenter;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public String getLabCenter() {
        return labCenter;
    }

    public void setLabCenter(String labCenter) {
        this.labCenter = labCenter == null ? null : labCenter.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}