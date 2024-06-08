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
public class BusinessDataVO implements Serializable {
    @ApiModelProperty("新增用户数")
    private Long newUsers;

    @ApiModelProperty("订单完成率")
    private double orderCompletionRate;

    @ApiModelProperty("营业额")
    private double turnover;

    @ApiModelProperty("平均客单价")
    private double unitPrice;

    @ApiModelProperty("有效订单数")
    private Integer validOrderCount;
}
