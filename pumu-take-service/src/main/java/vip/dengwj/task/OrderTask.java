package vip.dengwj.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.mapper.OrderMapper;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {
    @Resource
    private OrderMapper orderMapper;

    // 每分钟执行该任务
    //@Scheduled(cron = "0 * * * * ?")
    public void autoCancelOrder() {
        log.info("自动取消订单 {}", LocalDateTime.now());

        // 往前15分钟
        LocalDateTime orderTime = LocalDateTime.now().plusMinutes(-15);
        List<OrderEntity> orderList = orderMapper.getOrderByStatusAndOrderTime(OrderEntity.PENDING_PAYMENT, orderTime);

        orderList.forEach(order -> {
            order.setStatus(OrderEntity.CANCELLED);
            order.setCancelTime(LocalDateTime.now());
            order.setCancelReason("自动取消订单");
            // 这里可以批量更新，用 in(id, id, id, id, id)
            orderMapper.update(order);
        });
    }

    /**
     * 处理一直处于订单派送中的状态
     */
    // 每天凌晨1点触发一次
    //@Scheduled(cron = "0 0 1 * * ?")
    public void cancelOrder() {
        log.info("定时处理处于派送中的订单： {}", LocalDateTime.now());
        LocalDateTime orderTime = LocalDateTime.now().plusMinutes(-60);

        List<OrderEntity> orderList = orderMapper.getOrderByStatusAndOrderTime(OrderEntity.DELIVERY_IN_PROGRESS, orderTime);
        orderList.forEach(order -> {
            order.setStatus(OrderEntity.COMPLETED);
            orderMapper.update(order);
        });
    }
}
