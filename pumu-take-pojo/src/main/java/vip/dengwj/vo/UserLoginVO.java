package vip.dengwj.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel("用户对象")
@Builder
public class UserLoginVO {
    @ApiModelProperty("用户 id")
    private Long id;

    @ApiModelProperty("微信用户 openid")
    private String openid;

    @ApiModelProperty("jwt 令牌")
    private String token;
}
