package com.siec.system.common.constant;

/**
 * @Description: TODO
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
public enum StatusType {
    Y(1),
    N(0);

    private Integer code;

    private StatusType(Integer code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
