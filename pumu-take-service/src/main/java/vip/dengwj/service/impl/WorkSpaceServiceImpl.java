package vip.dengwj.service.impl;

import org.springframework.stereotype.Service;
import vip.dengwj.constant.StatusConstant;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.mapper.DishMapper;
import vip.dengwj.mapper.OrderMapper;
import vip.dengwj.mapper.SetmealMapper;
import vip.dengwj.mapper.UserMapper;
import vip.dengwj.service.WorkSpaceService;
import vip.dengwj.vo.BusinessDataVO;
import vip.dengwj.vo.OverviewDishesVO;
import vip.dengwj.vo.OverviewOrdersVO;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private DishMapper dishMapper;

    @Resource
    private SetmealMapper setmealMapper;

    /**
     * 查询今日运营数据
     */
    @Override
    public BusinessDataVO businessData() {
        LocalDate now = LocalDate.now();
        LocalDateTime begin = LocalDateTime.of(now, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(now, LocalTime.MAX);

        // 今日新增用户数
        Long newUsers = userMapper.getCountByCreateTime(begin, end);

        // 有效订单数
        Integer completed = orderMapper.completed(OrderEntity.COMPLETED);

        // 总条数
        Integer count = orderMapper.count(null);
        // 订单完成率
        double r = 0.0;
        // 防止除以 0 报错
        if (count != 0) {
            double rate = (double) completed / (double) count;
            DecimalFormat df = new DecimalFormat("#.00");
            r = Double.parseDouble(df.format(rate));
        }

        // 营业额
        double turnover = orderMapper.completedAmount(OrderEntity.COMPLETED);

        // 平均客单价
        double unitPrice = turnover / completed;

        return BusinessDataVO.builder()
            .newUsers(newUsers)
            .validOrderCount(completed)
            .orderCompletionRate(r)
            .turnover(turnover)
            .unitPrice(unitPrice)
            .build();
    }

    /**
     * 查询订单管理数据
     */
    @Override
    public OverviewOrdersVO overviewOrders() {
        // 全部订单数
        Integer allOrders = orderMapper.count(null);

        // 已取消数量
        Integer cancelledOrders = orderMapper.count(OrderEntity.CANCELLED);

        // 已完成
        Integer completedOrders = orderMapper.count(OrderEntity.COMPLETED);

        // 待派送
        Integer deliveredOrders = orderMapper.count(OrderEntity.CONFIRMED);

        // 待接单
        Integer waitingOrders = orderMapper.count(OrderEntity.TO_BE_CONFIRMED);

        return OverviewOrdersVO.builder()
            .allOrders(allOrders)
            .cancelledOrders(cancelledOrders)
            .completedOrders(completedOrders)
            .deliveredOrders(deliveredOrders)
            .waitingOrders(waitingOrders)
            .build();
    }

    /**
     * 查询菜品总览
     */
    @Override
    public OverviewDishesVO overviewDishes() {
        // 已停售菜品数量
        Integer discontinued = dishMapper.getDishCountByStatus(StatusConstant.DISABLE);

        // 已启售
        Integer sold = dishMapper.getDishCountByStatus(StatusConstant.ENABLE);

        return OverviewDishesVO.builder()
            .discontinued(discontinued)
            .sold(sold)
            .build();
    }

    /**
     * 查询套餐总览
     */
    @Override
    public OverviewDishesVO overviewSetmeals() {
        Integer discontinued = setmealMapper.getSetmealCountByStatus(StatusConstant.DISABLE);

        Integer sold = setmealMapper.getSetmealCountByStatus(StatusConstant.ENABLE);

        return OverviewDishesVO.builder()
            .discontinued(discontinued)
            .sold(sold)
            .build();
    }
}
