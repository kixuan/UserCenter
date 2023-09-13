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
    // 不能，因为这是枚举类
    // 因为枚举常量是有限的，每个常量都有不同的属性值，而不是通过字段赋值的方式来定义。
    // 因此，Lombok 的 @Data 注解不能自动识别枚举类中的属性。
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
