package com.example.usercenterbackend.exception;

import com.example.usercenterbackend.common.ErrorCode;
import lombok.Getter;

// TODO 运行时异常，又称不受检查异常
// 自定义异常类，继承RuntimeException，用于表示应用程序中的业务异常
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1449280272362390847L;
    private final int code;
    private final String description;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

}
