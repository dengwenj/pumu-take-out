package vip.dengwj.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜品分页返回的对象")
public class DishVo {
    @ApiModelProperty("菜品id")
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("分类 id")
    private Long categoryId;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("菜品图片")
    private String image;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("菜品状态")
    private Integer status;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("分类名称")
    private String categoryName;
}
