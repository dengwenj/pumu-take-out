package vip.dengwj.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBookEntity {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("用户 id")
    private Long userId;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("省份编码")
    private String provinceCode;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("城市编码")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区县编码")
    private String districtCode;

    @ApiModelProperty("区县名称")
    private String districtName;

    @ApiModelProperty("详情地址信息")
    private String detail;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("是否默认地址，1是，0否")
    private int isDefault;
}
