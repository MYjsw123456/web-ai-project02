package com.lmy.exception;

/**
 * 业务异常
 * 用于在业务校验不通过时抛出，由全局异常处理器统一捕获并返回错误提示信息
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
