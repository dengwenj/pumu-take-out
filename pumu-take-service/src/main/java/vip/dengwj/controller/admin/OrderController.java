package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.OrderCancelDTO;
import vip.dengwj.dto.OrderConfirmDTO;
import vip.dengwj.dto.OrderQueryDTO;
import vip.dengwj.dto.OrderRejectionDTO;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.entity.OrderQueryEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.OrderService;
import vip.dengwj.vo.OrderStatusVO;
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

    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatusVO> statistics() {
        OrderStatusVO data = orderService.statistics();
        return Result.success(data);
    }

    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderEntity> details(@PathVariable Long id) {
        OrderEntity order = orderService.orderDetail(id);
        return Result.success(order);
    }

    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrderConfirmDTO orderConfirmDTO) {
        orderService.confirm(orderConfirmDTO.getId());
        return Result.success();
    }

    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrderRejectionDTO orderRejectionDTO) {
        orderService.rejection(orderRejectionDTO);
        return Result.success();
    }

    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result adminCancel(@RequestBody OrderCancelDTO orderCancelDTO) {
        orderService.adminCancel(orderCancelDTO);
        return Result.success();
    }

    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result adminDelivery(@PathVariable Long id) {
        orderService.adminDelivery(id);
        return Result.success();
    }
}
