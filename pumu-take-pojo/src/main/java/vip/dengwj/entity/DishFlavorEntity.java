package vip.dengwj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜品口味实体")
public class DishFlavorEntity implements Serializable {
    @ApiModelProperty("菜品id")
    private Long dishId;

    @ApiModelProperty("口味id")
    private Long id;

    @ApiModelProperty(required = true, value = "口味名称")
    private String name;

    @ApiModelProperty(required = true, value = "口味值")
    private String value;
}
