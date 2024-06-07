package vip.dengwj.service.impl;

import org.springframework.stereotype.Service;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.mapper.OrderMapper;
import vip.dengwj.mapper.UserMapper;
import vip.dengwj.service.ReportService;
import vip.dengwj.vo.OrderReportVO;
import vip.dengwj.vo.TurnoverReportVO;
import vip.dengwj.vo.UserReportVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 营业额统计接口
     */
    @Override
    public TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end) {
        // 计算日期
        List<LocalDate> dateList = calcDate(begin, end);
        String strDate = dateList.stream().map(LocalDate::toString).collect(Collectors.joining(","));

        List<BigDecimal> totalList = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            // 可以用 like '2024-06-07%'，和 in 就可以批量了
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", OrderEntity.COMPLETED);

            BigDecimal price = orderMapper.getSumByMap(map);
            price = price == null ? BigDecimal.ZERO : price;
            totalList.add(price);
        }

        String strData = totalList.stream().map(BigDecimal::toString).collect(Collectors.joining(","));

        return TurnoverReportVO.builder()
            .dateList(strDate)
            .turnoverList(strData)
            .build();
    }

    /**
     * 用户数量统计
     */
    @Override
    public UserReportVO getUserReport(LocalDate begin, LocalDate end) {
        // 计算日期
        List<LocalDate> dateList = calcDate(begin, end);
        String strDate = dateList.stream().map(LocalDate::toString).collect(Collectors.joining(","));

        List<Long> newUsers = new ArrayList<>();
        List<Long> totalUsers = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);

            Long userCount = userMapper.getCountByCreateTime(beginTime, endTime);
            userCount = userCount == null ? 0 : userCount;
            newUsers.add(userCount);

            Long countAll = userMapper.getCountAll(endTime);
            countAll = countAll == null ? 0 : countAll;
            totalUsers.add(countAll);
        }
        String strNewUser = newUsers.stream().map(String::valueOf).collect(Collectors.joining(","));
        String strTotalUsers = totalUsers.stream().map(String::valueOf).collect(Collectors.joining(","));

        return UserReportVO.builder()
            .dateList(strDate)
            .newUserList(strNewUser)
            .totalUserList(strTotalUsers)
            .build();
    }

    /**
     * 订单统计
     */
    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = calcDate(begin, end);
        String strDate = dateList.stream().map(LocalDate::toString).collect(Collectors.joining(","));

        List<Integer> orderCountList = new ArrayList<>();
        List<Integer> totalByDateStatusList = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);

            // 获取该时间区间内所有订单
            Integer totalByDate = orderMapper.getTotalByDate(beginTime, endTime, null);
            totalByDate = totalByDate == null ? 0 : totalByDate;
            orderCountList.add(totalByDate);

            // 获取该时间区间内有效订单
            Integer totalByDateStatus = orderMapper.getTotalByDate(beginTime, endTime, OrderEntity.COMPLETED);
            totalByDateStatus = totalByDateStatus == null ? 0 : totalByDateStatus;
            totalByDateStatusList.add(totalByDateStatus);
        }
        String strOrderCount = orderCountList.stream().map(String::valueOf).collect(Collectors.joining(","));
        String strTotalByDateStatus = totalByDateStatusList.stream().map(String::valueOf).collect(Collectors.joining(","));

        // orderCountList.stream().reduce(33, (res, item) -> res + item);
        // orderCountList.stream().reduce((res, item) -> res + item);
        Integer orderCount = orderCountList.stream().reduce(Integer::sum).get();
        Integer validOrderCount = totalByDateStatusList.stream().reduce(Integer::sum).get();

        // 有效率，保留2位小数
        double r = 0.0;
        // 防止除以 0 报错
        if (orderCount != 0) {
            double rate = (double) validOrderCount / (double) orderCount;
            DecimalFormat df = new DecimalFormat("#.00");
            r = Double.parseDouble(df.format(rate));
        }

        return OrderReportVO.builder()
            .dateList(strDate)
            .orderCountList(strOrderCount)
            .validOrderCountList(strTotalByDateStatus)
            .totalOrderCount(orderCount)
            .validOrderCount(validOrderCount)
            .orderCompletionRate(r)
            .build();
    }

    /**
     * 知道开始日期，结束日期，计算出该区间的全部日期
     */
    private List<LocalDate> calcDate(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        return dateList;
    }
}
