package com.ep.utils;

/***
 * @author dep
 * @version 1.0
 * @date 2023-04-11 22:14
 */
public enum ReturnCode {
    S200(200,"请求成功"),
    S201(201,"创建成功"),
    S204(204,"删除成功"),

    S400(400,"请求的地址不存在或者包含不支持的参数"),
    S401(401,"未授权"),
    S403(403,"被禁止访问"),
    S404(404,"请求资源不存在"),
    S422(422,"错误"),

    INVALID_TOKEN(2001,"访问令牌不合法"),
    ACCESS_DENIED(2003,"没有权限访问该资源"),
    CLIENT_AUTHENTICATION_FAILED(1001,"客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002,"用户名或密码错误"),
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");

    //自定义状态码
    private final int code;
    //自定义描述
    private final String message;

    ReturnCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
