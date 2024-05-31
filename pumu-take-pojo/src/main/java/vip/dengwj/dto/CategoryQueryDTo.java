package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分类查询")
public class CategoryQueryDTo {
    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类类型：1为菜品分类，2为套餐分类")
    private Integer type;

    private Integer page = 1;
    private Integer pageSize = 10;
}
