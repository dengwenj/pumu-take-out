package vip.dengwj.exception;

/**
 * @date 2024/5/29 17:59
 * @author 朴睦
 * @description 业务异常
 */
public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
