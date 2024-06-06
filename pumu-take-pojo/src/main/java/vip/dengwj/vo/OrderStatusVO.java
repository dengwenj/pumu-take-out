package vip.dengwj.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusVO implements Serializable {
    @ApiModelProperty("待派送数量")
    private Integer confirmed;

    @ApiModelProperty("派送中数量")
    private Integer deliveryInProgress;

    @ApiModelProperty("待接单数量")
    private Integer toBeConfirmed;
}