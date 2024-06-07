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
public class OrderReportVO implements Serializable {
    @ApiModelProperty("日期列表，以逗号分隔")
    private String dateList;

    @ApiModelProperty("订单完成率")
    private double orderCompletionRate;

    @ApiModelProperty("订单数列表，以逗号分隔")
    private String orderCountList;

    @ApiModelProperty("订单总数")
    private Integer totalOrderCount;

    @ApiModelProperty("有效订单数")
    private Integer validOrderCount;

    @ApiModelProperty("有效订单数列表，以逗号分隔")
    private String validOrderCountList;
}
