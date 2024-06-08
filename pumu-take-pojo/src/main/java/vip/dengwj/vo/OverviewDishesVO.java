package vip.dengwj.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverviewDishesVO implements Serializable {
    @ApiModelProperty("已停售菜品数量")
    private Integer discontinued;

    @ApiModelProperty("已启售菜品数量")
    private Integer sold;
}
