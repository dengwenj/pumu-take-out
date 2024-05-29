package vip.dengwj.exception;

/**
 * @date 2024/5/29 21:50
 * @author 朴睦
 * @description
 */
public class AccountLockedException extends BaseException {
    public AccountLockedException(String msg) {
        super(msg);
    }
}
