package com.example.usercenterbackend.common;

public enum ErrorCode {
    SUCCESS(0, "OK", ""),
    PARAM_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求参数为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NOT_AUTH(40300, "无权限", ""),
    DUP_INFO(40400, "信息重复", ""),
    SYSTEM_ERROR(50000, "系统错误", "");
    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态码描述（详情）
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    // 这里能不能直接@Data注解实现
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}