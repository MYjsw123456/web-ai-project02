package com.lmy.exception;

import com.lmy.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public Result handlerException(Exception e){
        log.info("程序出错了",e);
        return Result.error("出错啦，请联系管理员");
    }

    @ExceptionHandler
    public Result handlerDuplicateKeyException(DuplicateKeyException e){
        log.info("程序出错了",e);
        String message = e.getMessage();
        String errMsg = message.substring(message.indexOf("Duplicate entry"));
        String[] arr = errMsg.split(" ");
        return Result.error(arr[2]+"已存在");
    }

    /**
     * 业务异常：将校验失败的提示信息原返回给前端
     */
    @ExceptionHandler
    public Result handlerBusinessException(BusinessException e){
        log.info("业务校验失败：{}", e.getMessage());
        return Result.error(e.getMessage());
    }
}
