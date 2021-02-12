package com.example.system.common.constant;

/**
 * @Description: 结果返回状态信息
 * @author: 老骨头（lgt）
 * @date: 2021/2/10
 */
public enum ResultStatus implements Status {
    /**
     * 成功状态码
     */
    SUCCESS(1, "成功"),
    /**
     * 参数状态码1000-1999
     */
    SERVICE_NOT_ACTIVITY(1000, "服务暂未开启"),
    SERVICE_ERROR(1001, "服务出现错误"),
    PARAM_ERROR(1002, "参数错误"),


    /**
     * 用户行为状态码2000-2999
     */
    USER_AUTH_ERROR(2000, "权限不足"),
    USER_NOT_LOGGED_IN(2001, "用户未登录，访问页面需要验证，请登录"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_NOT_EXIST(2004, "用户不存在"),
    USER_HAS_EXISTED(2005, "用户已存在"),
    USER_PASSWORD_CHANGE_ERROR(2006, "密码修改失败"),
    USER_FREQUENT_REQUEST(2007, "请求过于频繁"),
    USER_FREEZE(2008, "用户已被冻结"),
    USER_LOGIN_FAILURE(2009, "用户登录失败"),
    USER_LOGOUT_SUCCESS(2010, "退出登录成功"),
    USER_LOGOUT_FAILURE(2011, "退出登录失败"),
    USER_REGISTERED(2012, "用户名已被注册"),
    USER_PHONE_REGISTERED(2013, "手机号已被注册"),
    USER_EMAIL_REGISTERED(2014, "邮箱已被注册"),
    USER_REGISTER_SUCCESS(2015, "用户注册成功"),
    USER_REGISTER_FAILURE(2016, "用户注册失败");

    private int code;
    private String desc;

    ResultStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
