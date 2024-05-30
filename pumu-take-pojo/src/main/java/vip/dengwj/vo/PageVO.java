package vip.dengwj.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分页数据")
public class PageVO<T> {
    @ApiModelProperty("总条数")
    private int total;
    @ApiModelProperty("返回的数据")
    private List<T> records;
}
