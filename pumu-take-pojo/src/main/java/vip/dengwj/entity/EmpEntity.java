package vip.dengwj.entity;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("唯一标识")
    private Long id;
    @ApiModelProperty("员工名称")
    private String name;
    @ApiModelProperty("用户名")
    private String username;

    private String password;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("性别")
    private Integer sex;
    @ApiModelProperty("身份证")
    private String idNumber;
    @ApiModelProperty("员工状态")
    private Integer status;
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
    @ApiModelProperty("创建人id")
    private Long createUser;
    @ApiModelProperty("更新人id")
    private Long updateUser;
}
