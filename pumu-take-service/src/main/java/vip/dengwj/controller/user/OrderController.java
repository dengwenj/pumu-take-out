package vip.dengwj.controller.user;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.OrderSubmitDTO;
import vip.dengwj.dto.OrdersPaymentDTO;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.OrderService;
import vip.dengwj.vo.OrderPaymentVO;
import vip.dengwj.vo.OrderSubmitVO;
import vip.dengwj.vo.PageVO;

@RestController
@RequestMapping("/user/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("提交订单")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrderSubmitDTO orderSubmitDTO) {
        OrderSubmitVO data = orderService.submit(orderSubmitDTO);
        return Result.success(data);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        //log.info("订单支付：{}", ordersPaymentDTO);
        //OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        //log.info("生成预支付交易单：{}", orderPaymentVO);
        //return Result.success(orderPaymentVO);

        // 模拟支付成功
        //业务处理，修改订单状态、来单提醒
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success(new OrderPaymentVO());
    }

    @GetMapping("/historyOrders")
    @ApiOperation("历史订单")
    public Result<PageVO<OrderEntity>> page(Integer page, Integer pageSize, Integer status) {
        PageVO<OrderEntity> data = orderService.page(page, pageSize, status);
        return Result.success(data);
    }

    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderEntity> orderDetail(@PathVariable Long id) {
        OrderEntity order = orderService.orderDetail(id);
        return Result.success(order);
    }

    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }
}
