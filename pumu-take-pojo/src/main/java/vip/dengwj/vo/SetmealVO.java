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
@ApiModel("套餐返回对象")
public class SetmealVO {
    @ApiModelProperty("套餐 id")
    private Long id;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("套餐图片")
    private String image;

    @ApiModelProperty("套餐价")
    private BigDecimal price;

    @ApiModelProperty("套餐起售状态")
    private Integer status;

    @ApiModelProperty("分类 id")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private Long categoryName;

    @ApiModelProperty("最后操作时间")
    private LocalDateTime updateTime;
}
