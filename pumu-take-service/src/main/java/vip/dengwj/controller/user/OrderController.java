package vip.dengwj.controller.user;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.dto.OrderSubmitDTO;
import vip.dengwj.result.Result;
import vip.dengwj.service.OrderService;
import vip.dengwj.vo.OrderSubmitVO;

@RestController
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation("提交订单")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrderSubmitDTO orderSubmitDTO) {
        OrderSubmitVO data = orderService.submit(orderSubmitDTO);
        return Result.success(data);
    }
}
