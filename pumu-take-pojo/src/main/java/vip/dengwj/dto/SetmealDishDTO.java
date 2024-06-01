package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "套餐菜品传递对象")
public class SetmealDishDTO implements Serializable {
    @ApiModelProperty("菜品 id")
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("菜品分数")
    private Integer copies;
}
