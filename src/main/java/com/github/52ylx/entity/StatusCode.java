package com.gitee.ylx.entity;

/**
 *  状态码
 */
public class StatusCode {
    //成功
    public static final int OK=20000;

    // 失败
    public static final int ERROR=20001;

    // 用户名或密码错误
    public static final int LOGINERROR=20002;

    // 权限不足
    public static final int ACCESSERROR=20003;

    // 远程调用失败
    public static final int REMOTEERROR=20004;

    // 重复操作
    public static final int REPERROR=20005;

    // token过期
    public static final int TOKERPAST=20006;

    // 当前密码仍旧为默认密码，需要先修改密码
    public static final int DEFAULTPASSWORD=20007;

    // 第一次登录，需询问是否设置密码
    public static final int FIRSTLOGIN=20008;

    // 患者小程序绑定个人信息时，根据输入的身份证号查询到已有患者信息，询问是否绑定该患者
    public static final int REBINDING=20009;

    // 药师和医生登录时查询到为备案
    public static final int NOTFILED=20010;

    // 添加就诊人的时候，如果查询到重复的身份证号，给前台返回这个code码，进行相应的提示
    public static final int REPEAT_IDNO=20011;

    //多数据源查数据库时 没有传医院ID
    public static final int NOT_HOSID = 20012;

    //某主体删除关联的角色时，进行提示
    public static final int SUBJECT_DEL_IDENTITY = 20013;

    //注册的时候，手机号不同身份证号相同，且只查出一条时，给老手机发送验证码，再调用其他接口
    public static final int TELNO_ONE_PHONECODE = 20014;

    //患者注册的时候，手机号和身份证号，查出两条时，给老手机发送验证码，再调用其他接口
    public static final int TWO_DATA_PHONECODE = 20015;

    //医生登录时，如果手机号未注册过医生，提示注册，则返回此code码
    public static final int NOT_EXIST_DOC = 20016;

    //医生注册时，如果手机号已经存在医生，提示登录，则返回此code码
    public static final int IS_EXIST_DOC = 20017;

    //企业微信该医生在父企业不可见
    public static final int INVISIBLE = 20018;

    //患者通过医生在企业微信注册，给患者老手机号发送验证码
    public static final int PATIENT_ENTERPRISE_REGISTER_OLD_TELNO = 20019;

    //患者注册时，需要患者输入实名信息
    public static final int PATIENT_REGISTER_NEED_REAL = 20020;



    /** 自定义业务异常*/
    public static final int RESULT_SERVICE_ERR=50000;
    /** 业务异常*/
    public static final int SERVICE_ERR=50001;
    /** 参数验证异常*/
    public static final int VIOLATION_ERR=50002;
    /** 缺少参数异常*/
    public static final int MISS_ERR=50003;
    /** 参数解析异常*/
    public static final int PARS_ERR=50004;
    /** 接口不存在*/
    public static final int NO_HADNLER_ERR=50005;
    /** 接口调用次数到达上限*/
    public static final int HADNLER_CALLNUMBER_UPPERLIMIT=50006;
    /** 客户端与服务版本不一致*/
    public static final int VERSION_ERR=50007;


}
