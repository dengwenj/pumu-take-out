package vip.dengwj.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.exception.BaseException;
import vip.dengwj.result.Result;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @date 2024/5/29 17:54
 * @author 朴睦
 * @description 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Exception.class 捕获所有异常
    @ExceptionHandler
    public Result handleException(BaseException e) {
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result handleException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            return Result.error(split[2] + MessageConstant.ALREADY_EXISTS);
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
