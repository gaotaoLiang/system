package com.demo.system.util;


import com.demo.system.common.constant.Status;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 自定义json返回格式
 * @author: 老骨头（lgt）
 */
@Data
public class ResponseResult<T>{

    // 请求是否成功（true：成功，false：失败）
    private boolean success;

    // 请求信息
    private Object message;

    // 返回的数据
    private T data;

    public ResponseResult(T data) {
        this.success = true;
        this.data = data;
    }

    public ResponseResult() {
        this.success = true;
    }

    /**
     * 返回成功
     */
    public static <T> ResponseResult<T> success(){
       return new ResponseResult();
    }

    /**
     * 返回带对象数据的JSON
     */
    public static <T> ResponseResult<T> success(T data){
        return  new ResponseResult(data);
    }

    public static <T> ResponseResult<T> success(Status status){
        ResponseResult<T> responseResult = new ResponseResult();
        responseResult.setSuccess(true);
        responseResult.setMessage(buildMessage(status));
        return responseResult;
    }

    /**
     *自定义错误返回信息
     */
    public static <T> ResponseResult<T> error(){
        ResponseResult<T> responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        return responseResult;
    }

    /**
     *自定义错误返回信息
     */
    public static <T> ResponseResult<T> error(Object message){
        ResponseResult<T> responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static <T> ResponseResult<T> error(Status status){
        ResponseResult<T> responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setMessage(buildMessage(status));
        return responseResult;
    }

    protected static Map<String, Object> buildMessage(Status status){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", status.getCode());
        map.put("desc", status.getDesc());
        return map;
    }
}