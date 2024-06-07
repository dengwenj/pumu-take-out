package vip.dengwj.service.impl;

import org.springframework.stereotype.Service;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.mapper.OrderMapper;
import vip.dengwj.service.ReportService;
import vip.dengwj.vo.TurnoverReportVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    /**
     * 营业额统计接口
     */
    @Override
    public TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end) {
        // 计算日期
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

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
}
