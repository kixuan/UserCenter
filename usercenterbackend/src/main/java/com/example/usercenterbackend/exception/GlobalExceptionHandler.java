package com.example.usercenterbackend.exception;

import com.example.usercenterbackend.common.BaseResponse;
import com.example.usercenterbackend.common.ErrorCode;
import com.example.usercenterbackend.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 注解表示这是一个全局异常处理器，用于处理控制器中抛出的异常。
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    // 表示这个方法会处理BusinessException类型的异常
    @ExceptionHandler(BusinessException.class)
    public BaseResponse handleBusinessException(BusinessException e) {
        log.info("BusinessException：{}", e.getMessage());
        return ResultUtil.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleException(BusinessException e) {
        log.info("RuntimeException：{}", e.getMessage());
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
