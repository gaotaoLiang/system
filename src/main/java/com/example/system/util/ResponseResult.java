package com.example.system.util;


import java.io.Serializable;

/**
 * @Description: 自定义json返回格式（带返回数据）
 * @author: 老骨头（lgt）
 * @date: 2019/9/17  19:34
 */
public class ResponseResult<T> implements Serializable {

    // http 状态码
    private int code = 200;

    // 返回信息
    private boolean success = true;

    // 返回的数据
    private T data;


    public ResponseResult(T data) {
        this.data = data;
    }

    public ResponseResult(int code, boolean success , T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}