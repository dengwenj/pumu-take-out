package vip.dengwj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户实体对象")
public class UserEntity {
    @ApiModelProperty("用户 id")
    private Long id;

    @ApiModelProperty("微信用户 openid")
    private String openid;

    @ApiModelProperty("用户名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别，1.男，2.女")
    private Integer sex;

    @ApiModelProperty("身份证")
    private String idNumber;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
