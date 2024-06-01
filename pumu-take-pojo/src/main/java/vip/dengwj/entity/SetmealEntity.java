package vip.dengwj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "套餐实体")
public class SetmealEntity implements Serializable {
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

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人")
    private Long createUser;

    @ApiModelProperty("更新人")
    private Long updateUser;
}
