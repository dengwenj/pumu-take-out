package vip.dengwj.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("套餐菜品对象")
public class SetmealDishVO implements Serializable {
    @ApiModelProperty("份数")
    private String copies;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("菜品图片")
    private String image;

    @ApiModelProperty("菜品名称")
    private String name;
}
