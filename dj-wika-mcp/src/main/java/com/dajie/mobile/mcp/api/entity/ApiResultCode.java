/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.entity;

/**
 * "E"表示“error”(错误)；"SYS"表示"system"（系统平台级的）;"biz"表示“business”（业务级的）
 * 
 * @author wei.cheng
 * 
 */
public final class ApiResultCode {

    // 成功
    public final static int SUCCESS = 0;

    // <1000的为系统错误
    public final static int E_SYS_UNKNOWN = 1;// unknown

    public final static int E_SYS_PARAM = 2;// invalid or unkown parameter

    public final static int E_SYS_PERMISSION_DENY = 3;// permission deny

    public final static int E_SYS_REQUEST_TOO_FAST = 4;// user request is too
                                                       // fast

    public final static int E_SYS_ANTISPAM = 5;// antispam相关

    public final static int E_SYS_INVALID_APP_ID = 6;// invalid appid

    public final static int E_SYS_INVALID_TICKET = 7;// invalid tikect

    public final static int E_SYS_INVALID_SIG = 8;// invalid sig

    public final static int E_SYS_INVALID_VERSION = 9;// invalid version

    public final static int E_SYS_UNKNOWN_METHOD = 10;// unknown method

    public final static int E_SYS_UNKNOWN_RESULT_FORMAT = 11;// unknown results
                                                             // format

    public final static int E_SYS_RPC_ERROR = 12;// RPC error

    // >=1000且<10000为业务级错误
    public final static int E_BIZ_REGISTER_FAILED = 1001;// register failed:no reason
    
    public final static int E_BIZ_LOGIN_FAILED = 1001;// login failed:no reason

    public final static int E_BIZ_LOGIN_NO_ACCOUNT = 1002;// login
                                                          // failed:NO_ACCOUNT

    public final static int E_BIZ_LOGIN_WRONG_PASSWORD = 1003;// login
                                                              // failed:WRONG_PASSWORD

    public final static int E_BIZ_SAFE_CAPTCHA = 1004; // 需要图形验证码

    public final static int E_BIZ_PHOHE_NUMBER_USED = 1005; // 该手机号已使用，请更换手机号绑定

    public final static int E_BIZ_USER_BIND_UNVERIFIED = 1006; // 用户尚未进行绑定验证操作

    public final static int E_BIZ_SAFE_VERIFY_CODE_INVALID = 1007; // 验证码错误

    public final static int E_BIZ_BATCH_RUN_CYCLE_CALL = 1008; // 批处理时，循环调用了

    public final static int E_BIZ_RPC_FORBIT = 1009; // 无权限,拒绝服务 无权限查看照片

    public final static int E_BIZ_RPC_BLOCKED = 1010; // 拉黑异常   黑名单限制

    public final static int E_BIZ_RPC_TOO_FAST = 1011; // 过频繁异常 用户输入UGC异常

    public final static int E_BIZ_RPC_ILLEGAL_CONTENT = 1012; // 内容违禁，包含非法词等    用户输入文字异常

    public final static int E_BIZ_RPC_ILLEGAL_LENGTH = 1013; // 内容长度限制，过长或者为空   输入内容太长
    
    public final static int E_BIZ_RPC_UGC_SYNC_ERR = 1014; // 同步第三方的异常（分享）

    public final static int E_BIZ_RPC_THIRD_ERR = 1015; // 第三方接口异常  如无法加载到第三方的信息，比如超时等

    public final static int E_BIZ_RPC_USER_NAME_ALREADY_EXIST = 1016; // 用户唯一（T2 ID）标识已经存在  修改T2 ID已经存在

    public final static int E_BIZ_RPC_EXCEED_MODIFY_LIMIT = 1017; // 修改T2 ID超过次数限制  超出当天操作数量限制

    public final static int E_BIZ_RPC_IMPORT_USER_INFO_FAILED = 1018; // 用户 导入失败（用户登录）

    public final static int E_BIZ_RPC_INVALID_ACCESS_TOKEN = 1019; // 登录的票过期    同步第三方或者登录时

    public final static int E_RPC_TIMEOUT = 1020; // RPC超时异常

    public final static int E_BIZ_RPC_RESOURCE_NOT_EXISTS = 1021; // 该内容不存在
    
    public final static int E_BIZ_RPC_SUBRESOURCE_NOT_EXISTS = 1022; // 附属资源不存在

}
