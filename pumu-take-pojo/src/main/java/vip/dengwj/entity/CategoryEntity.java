package vip.dengwj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "分类对象")
public class CategoryEntity {
    @ApiModelProperty("唯一 id")
    private Long id;

    @ApiModelProperty("分类类型")
    private Integer type;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类排序")
    private Integer sort;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人 id")
    private Long createUser;

    @ApiModelProperty("更新人 id")
    private Long updateUser;
}
