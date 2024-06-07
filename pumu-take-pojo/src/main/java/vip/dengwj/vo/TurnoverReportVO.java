package vip.dengwj.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TurnoverReportVO {
    @ApiModelProperty("日期列表，日期之间以逗号分隔")
    private String dateList;

    @ApiModelProperty("营业额列表，营业额之间以逗号分隔")
    private String turnoverList;
}
