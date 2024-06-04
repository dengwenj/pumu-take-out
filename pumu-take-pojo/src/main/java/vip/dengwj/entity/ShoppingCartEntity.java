package vip.dengwj.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartEntity {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品图片")
    private String image;

    @ApiModelProperty("用户 id")
    private Long userId;

    @ApiModelProperty("菜品 id")
    private Long dishId;

    @ApiModelProperty("套餐 id")
    private Long setmealId;

    @ApiModelProperty("口味")
    private String dishFlavor;

    @ApiModelProperty("商品数量")
    private Integer number;

    @ApiModelProperty("商品单价")
    private BigDecimal amount;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
