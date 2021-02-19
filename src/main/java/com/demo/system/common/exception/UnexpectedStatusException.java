package com.demo.system.common.exception;


import com.demo.system.common.constant.Status;
import lombok.Data;

/**
 * 自定义异常
 * @date 2019/7/13
 */
@Data
public class UnexpectedStatusException extends RuntimeException {

    private static final long serialVersionUID = 6578328526462367623L;

    private Integer code = 500;

    /**
     * 返回自定义的状态码和异常描述
     */
    public UnexpectedStatusException(Status status, Integer code) {
        super(status.getDesc());
        this.code = code;
    }

    /**
     * 只返回异常信息(默认返回500)
     */
    public UnexpectedStatusException(Status status) {
        super(status.getDesc());
    }

    /**
     * 返回异常信息
     */
    public UnexpectedStatusException(Throwable throwable) {
        super(throwable);
    }

}
