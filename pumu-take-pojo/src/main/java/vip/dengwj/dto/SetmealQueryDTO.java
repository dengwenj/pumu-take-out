package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "套餐查询传递对象")
public class SetmealQueryDTO implements Serializable {
    @ApiModelProperty("分类 id")
    private Long categoryId;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("套餐起售状态")
    private Integer status;

    private Integer page;

    private Integer pageSize;
}
