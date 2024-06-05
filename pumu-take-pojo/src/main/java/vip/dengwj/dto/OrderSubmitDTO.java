package vip.dengwj.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitDTO implements Serializable {
    @ApiModelProperty("地址簿 id")
    private Long addressBookId;

    @ApiModelProperty("付款方式")
    private String payMethod;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    @ApiModelProperty("配送时间 1、立即送出，2、选择具体时间")
    private Integer deliveryStatus;

    @ApiModelProperty("餐具数量")
    private Integer tablewareStatus;

    @ApiModelProperty("打包费")
    private Integer packAmount;

    @ApiModelProperty("总金额")
    private BigDecimal amount;
}
