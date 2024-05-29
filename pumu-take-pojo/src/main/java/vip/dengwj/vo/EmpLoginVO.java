package vip.dengwj.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpLoginVO {
    // 员工 id
    private Long id;
    // 用户名
    private String username;
    // 姓名
    private String name;
    // 令牌
    private String token;
}
