package vip.dengwj.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCancelDTO {
    @ApiModelProperty("主键 id")
    private Long id;

    @ApiModelProperty("取消原因")
    private String cancelReason;
}
