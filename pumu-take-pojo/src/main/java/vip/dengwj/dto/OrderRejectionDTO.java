package vip.dengwj.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRejectionDTO {
    @ApiModelProperty("主键 id")
    private Long id;

    @ApiModelProperty("拒单原因")
    private String rejectionReason;
}
