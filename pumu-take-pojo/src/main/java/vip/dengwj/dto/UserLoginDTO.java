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
@ApiModel(description = "用户登录传递对象")
public class UserLoginDTO implements Serializable {
    @ApiModelProperty("js_code")
    private String code;
}
