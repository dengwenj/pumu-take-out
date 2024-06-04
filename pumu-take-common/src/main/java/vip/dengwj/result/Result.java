package vip.dengwj.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 后端统一返回的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    // 编码：1成功，0和其它数字为失败
    private int code;
    // 返回的消息
    private String msg;
    // 返回的数据
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(1, "success", data);
    }

    public static <T> Result<T> success() {
        return new Result<>(1, "success", null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(0, msg, null);
    }
}
