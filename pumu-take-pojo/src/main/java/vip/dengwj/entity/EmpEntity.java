package vip.dengwj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpEntity {
    private Long id;

    private String name;

    private String username;

    private String password;

    private String phone;

    private Integer sex;

    private String idNumber;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}
