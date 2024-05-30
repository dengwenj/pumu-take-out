package vip.dengwj.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("员工查询")
public class EmpQueryDTO {
    @ApiModelProperty("员工名称")
    private String name;
    private Integer page = 1;
    private Integer pageSize = 10;
}
