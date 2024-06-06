package vip.dengwj.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderQueryDTO {
    @ApiModelProperty("开始下单时间")
    private LocalDateTime beginTime;

    @ApiModelProperty("结束下单时间")
    private LocalDateTime endTime;

    @ApiModelProperty("订单号")
    private String number;

    private Integer page;

    private Integer pageSize;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("订单状态")
    private Integer status;
}
