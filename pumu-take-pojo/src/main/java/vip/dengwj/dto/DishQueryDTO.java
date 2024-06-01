package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜品分页传递对象")
public class DishQueryDTO {
    private Integer page;

    private Integer pageSize;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品状态")
    private String status;
}
