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
public class SalesTop10ReportVO implements Serializable {
    @ApiModelProperty("商品名称列表，以逗号分隔")
    private String nameList;

    @ApiModelProperty("销量列表，以逗号分隔")
    private String numberList;
}
