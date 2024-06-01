package vip.dengwj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "套餐菜品实体对象")
public class SetmealDishEntity implements Serializable {
    @ApiModelProperty("套餐菜品 id")
    private Long id;

    @ApiModelProperty("套餐 id")
    private Long setmealId;

    @ApiModelProperty("菜品 id")
    private Long dishId;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("菜品分数")
    private Integer copies;
}
