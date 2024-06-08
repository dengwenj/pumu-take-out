package vip.dengwj.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverviewOrdersVO implements Serializable {
    @ApiModelProperty("全部订单")
    private Integer allOrders;

    @ApiModelProperty("已取消数量")
    private Integer cancelledOrders;

    @ApiModelProperty("已完成数量")
    private Integer completedOrders;

    @ApiModelProperty("待派送数量")
    private Integer deliveredOrders;

    @ApiModelProperty("待接单数量")
    private Integer waitingOrders;
}
