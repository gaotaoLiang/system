package com.example.system.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 自定义json返回格式（不用返回数据）
 * @author: 老骨头（lgt）
 * @date: 2019/9/17  19:34
 */
@Data
public class Result<T> implements Serializable {

    private Integer code = 200;

    private boolean success = true;

    private String messages = "操作成功";

    public Result() { }

    /**
     * 操作成功默认返回"操作成功"信息，以及200状态码
     * @return
     */
    public Result<T>  success(){
        return this;
    }

    /**
     * 操作成功自定义返回信息，默认返回200状态码
     * @param messages
     * @return
     */
    public Result<T>  success(String messages){
        this.messages = messages;
        return this;
    }

    /**
     * 操作失败默认返回"操作失败"信息，以及500状态码
     * @return
     */
    public Result<T> error500(){
        this.code = 500;
        this.success = false;
        this.messages = "操作失败";
        return this;
    }

    /**
     * 操作失败自定义返回信息，以及状态码
     * @param code
     * @param messages
     * @return
     */
    public Result<T> error(Integer code, String messages){
        this.code = code;
        this.success = false;
        this.messages = messages;
        return this;
    }

}
