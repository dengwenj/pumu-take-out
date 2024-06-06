package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.dto.OrderQueryDTO;
import vip.dengwj.entity.OrderQueryEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.OrderService;
import vip.dengwj.vo.PageVO;

import javax.annotation.Resource;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Api(tags = "订单管理")
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageVO<OrderQueryEntity>> adminPage(OrderQueryDTO orderQueryDTO) {
        PageVO<OrderQueryEntity> list = orderService.adminPage(orderQueryDTO);
        return Result.success(list);
    }
}
