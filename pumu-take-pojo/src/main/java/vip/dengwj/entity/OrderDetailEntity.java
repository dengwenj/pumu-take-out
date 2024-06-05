package vip.dengwj.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailEntity implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品图片路径")
    private String image;

    @ApiModelProperty("订单 id")
    private Long orderId;

    @ApiModelProperty("菜品 id")
    private Long dishId;

    @ApiModelProperty("套餐 id")
    private Long setmealId;

    @ApiModelProperty("菜品口味")
    private String dishFlavor;

    @ApiModelProperty("商品数量")
    private Integer number;

    @ApiModelProperty("商品单价")
    private BigDecimal amount;
}
