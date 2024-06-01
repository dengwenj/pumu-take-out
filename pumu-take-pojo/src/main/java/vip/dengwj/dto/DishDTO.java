package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vip.dengwj.entity.DishFlavorEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "菜品对象")
public class DishDTO implements Serializable {
    @ApiModelProperty(required = true, value = "分类id")
    private Long categoryId;

    @ApiModelProperty("菜品描述")
    private String description;

    private List<DishFlavorEntity> flavors;

    @ApiModelProperty("菜品id")
    private Long id;

    @ApiModelProperty(required = true, value = "菜品图片")
    private String image;

    @ApiModelProperty(required = true, value = "菜品名称")
    private String name;

    @ApiModelProperty(required = true, value = "菜品价格")
    private BigDecimal price;

    @ApiModelProperty("菜品状态，1.起售，2.停售")
    private Integer status;
}
