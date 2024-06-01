package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "套餐传递对象")
public class SetmealDTO implements Serializable {
    @ApiModelProperty("套餐 id")
    private Long id;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("分类 id")
    private Long categoryId;

    @ApiModelProperty("套餐价格")
    private BigDecimal price;

    @ApiModelProperty("套餐图片")
    private String image;

    @ApiModelProperty("套餐描述")
    private String description;

    @ApiModelProperty("套餐状态")
    private Integer status;

    @ApiModelProperty("套餐菜品")
    private List<SetmealDishDTO> setmealDishDTOList;
}
