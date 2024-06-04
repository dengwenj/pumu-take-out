package vip.dengwj.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {
    @ApiModelProperty("菜品 id")
    private Long dishId;

    @ApiModelProperty("套餐id")
    private Long setmealId;

    @ApiModelProperty("口味")
    private String dishFlavor;
}
