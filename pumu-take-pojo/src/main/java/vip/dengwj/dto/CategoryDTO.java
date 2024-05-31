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
@ApiModel(description = "分类传递数据")
public class CategoryDTO implements Serializable {
    @ApiModelProperty("唯一标识")
    private Long id;

    @ApiModelProperty("分类名称，唯一")
    private String name;

    @ApiModelProperty("分类排序")
    private Integer sort;

    @ApiModelProperty("分类类型：1为菜品分类，2为套餐分类")
    private Integer type;
}
