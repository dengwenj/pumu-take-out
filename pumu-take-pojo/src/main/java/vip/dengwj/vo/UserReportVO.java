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
public class UserReportVO implements Serializable {
    @ApiModelProperty("日期列表，以逗号分隔")
    private String dateList;

    @ApiModelProperty("新增用户数列表，以逗号分隔")
    private String newUserList;

    @ApiModelProperty("总用户量列表，以逗号分隔")
    private String totalUserList;
}
