package vip.dengwj.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.dengwj.result.Result;

/**
 * @date 2024/5/29 17:54
 * @author 朴睦
 * @description 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Exception.class 捕获所有异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception  e) {
        return Result.error(e.getMessage());
    }
}
