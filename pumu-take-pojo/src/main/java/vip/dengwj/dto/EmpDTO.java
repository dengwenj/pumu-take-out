package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "员工对象")
public class EmpDTO implements Serializable {
    @ApiModelProperty("唯一 id")
    private Long id;

    @ApiModelProperty("员工名称")
    private String name;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("身份证")
    private String idNumber;
}
