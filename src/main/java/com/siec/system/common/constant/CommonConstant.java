package com.siec.system.common.constant;

public interface CommonConstant {

    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
    public static final Integer STATUS_DISABLE = -1;

    /**
     * 删除标志
     */
    public static final Integer DEL_FLAG_1 = 1;

    /**
     * 未删除
     */
    public static final Integer DEL_FLAG_0 = 0;

    /**
     * 系统日志类型： 登录
     */
    public static final int LOG_TYPE_1 = 1;

    /**
     * 系统日志类型： 操作
     */
    public static final int LOG_TYPE_2 = 2;


    /**
     * {@code 500 Server Error} (HTTP/1.0 - RFC 1945)
     */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /**
     * {@code 200 OK} (HTTP/1.0 - RFC 1945)
     */
    public static final Integer SC_OK_200 = 200;

    /**
     * 访问权限认证未通过 510
     */
    public static final Integer SC_JEECG_NO_AUTHZ = 510;

    /**
     * ---------------------------------------缓存------------------------------------------
     */
    /**
     * 缓存前缀
     */
    //登录用户拥有角色缓存KEY
    public static String LOGIN_USER_CACHERULES_ROLE = "loginUser_cacheRules::Roles_";
    //登录用户拥有权限缓存KEY
    public static String LOGIN_USER_CACHERULES_PERMISSION = "loginUser_cacheRules::Permissions_";
    //登录用户令牌缓存KEY
    public static final String PREFIX_USER_TOKEN = "prefix:user::Token_";
    //用户注册邮箱验证码
    public static final String USER_REGISTER_EMAIL_CODE_EMAIL = "user:register:email:code::Email_";
    //用户找回密码邮箱验证码
    public static final String USER_FORGET_EMAIL_CODE_USERNAME = "user:forget:email:code::Username_";
    /**
     * 缓存过期时间
     */
    //guava缓存过期时间（结合redis使用二级缓存机制）
    public static final Integer DEFAULT_GUAVA_CACHE_DURATION_IN_MIN = 60;   //分钟
   //手机短信验证码过期时间
    public static final int SMS_OVER_TIME = 300; //300秒即是5分钟
    /**
     * guava/redis二级缓存监听的key
     */
    //项目申请redis发布监听到的key
    public static final String APP_PROJECT_ALL_CACHE = "app:project:all:cache";
    //用户项目申请key对应自己的缓存
    public static final String APP_PROJECT_USERNAME = "app:project:Username_";

/**
 * _________________________________________________________________________________________________________
 */

    /**
     * 0：一级菜单
     */
    public static final Integer MENU_TYPE_0 = 0;
    /**
     * 1：子菜单
     */
    public static final Integer MENU_TYPE_1 = 1;
    /**
     * 2：按钮权限
     */
    public static final Integer MENU_TYPE_2 = 2;

    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    public static final String MSG_TYPE_UESR = "USER";
    public static final String MSG_TYPE_ALL = "ALL";

    /**
     * 发布状态（0未发布，1已发布，2已撤销）
     */
    public static final String NO_SEND = "0";
    public static final String HAS_SEND = "1";
    public static final String HAS_CANCLE = "2";

    /**
     * 阅读状态（0未读，1已读）
     */
    public static final String HAS_READ_FLAG = "1";
    public static final String NO_READ_FLAG = "0";

    /**
     * 优先级（L低，M中，H高）
     */
    public static final String PRIORITY_L = "L";
    public static final String PRIORITY_M = "M";
    public static final String PRIORITY_H = "H";

    /**
     * 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
     */
    public static final String SMS_TPL_TYPE_0 = "0";
    public static final String SMS_TPL_TYPE_1 = "1";
    public static final String SMS_TPL_TYPE_2 = "2";

    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";

    /**
     * 同步工作流引擎1同步0不同步
     */
    public static final String ACT_SYNC_0 = "0";
    public static final String ACT_SYNC_1 = "1";

    /**
     * 消息类型1:通知公告2:系统消息
     */
    public static final String MSG_CATEGORY_1 = "1";
    public static final String MSG_CATEGORY_2 = "2";

    /**
     * 是否配置菜单的数据权限 1是0否
     */
    public static final Integer RULE_FLAG_0 = 0;
    public static final Integer RULE_FLAG_1 = 1;

    /**
     * 是否用户已被冻结 1(解冻)正常 0冻结
     */
    public static final Integer USER_UNFREEZE = 1;
    public static final Integer USER_FREEZE = 0;

    /**
     * 字典翻译文本后缀
     */
    public static final String DICT_TEXT_SUFFIX = "_dictText";

    /**
     * 全局密钥密码
     */
    public static final String SECRET_KEY_PASSWORD = "siec_auth_secret_key_password";

    /**
     * 邮箱地址
     */
    public static final String EMAIL_ADDRESS = "15119689283@163.com";


}
