package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.result.Result;
import vip.dengwj.service.WorkSpaceService;
import vip.dengwj.vo.BusinessDataVO;
import vip.dengwj.vo.OverviewOrdersVO;

import javax.annotation.Resource;

@RestController
@Api(tags = "工作台接口")
@RequestMapping("/admin/workspace")
public class WorkSpaceController {
    @Resource
    private WorkSpaceService workSpaceService;

    @GetMapping("/businessData")
    @ApiOperation("查询今日运营数据")
    public Result<BusinessDataVO> businessData() {
        BusinessDataVO data = workSpaceService.businessData();
        return Result.success(data);
    }

    @GetMapping("/overviewOrders")
    @ApiOperation("查询订单管理数据")
    public Result<OverviewOrdersVO> overviewOrders() {
        OverviewOrdersVO data = workSpaceService.overviewOrders();
        return Result.success(data);
    }
}
