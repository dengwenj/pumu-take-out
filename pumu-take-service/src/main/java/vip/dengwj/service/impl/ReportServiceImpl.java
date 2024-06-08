package vip.dengwj.service.impl;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import vip.dengwj.dto.GoodsSalesDTO;
import vip.dengwj.entity.OrderDetailEntity;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.mapper.*;
import vip.dengwj.service.ReportService;
import vip.dengwj.service.WorkSpaceService;
import vip.dengwj.vo.*;
import vip.dengwj.websocket.WebSocketServer;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private WorkSpaceService workSpaceService;

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
     * 销售前10，sql 版
     */
    @Override
    public SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> list = orderDetailMapper.getGoodsSalesTop10(beginTime, endTime, null);
        String names = list.stream().map(GoodsSalesDTO::getName).collect(Collectors.joining(","));
        String numbers = list.stream().map((item) -> item.getNumber() + "").collect(Collectors.joining(","));

        return SalesTop10ReportVO.builder()
            .numberList(numbers)
            .nameList(names)
            .build();
    }

    /**
     * 数据导出
     */
    @Override
    public void export(HttpServletResponse response) throws IOException {
        // 查询进30天的
        // 减30天
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now().minusDays(1);
        // 加一天
        LocalDate localDate = dateBegin.plusDays(1);

        // 获取数据
        BusinessDataVO businessDataVO = workSpaceService.businessData();

        // 根据模版创建 excel
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("vip/dengwj/template/运营数据报表模板.xlsx");
        assert is != null;
        XSSFWorkbook excel = new XSSFWorkbook(is);
        XSSFSheet sheet = excel.getSheetAt(0);

        // 获取第一行
        XSSFRow row = sheet.getRow(1);
        row.getCell(1).setCellValue("时间：" + LocalDateTime.now());

        // 获取第四行
        XSSFRow row4 = sheet.getRow(3);
        row4.getCell(2).setCellValue(businessDataVO.getTurnover()); // 营业额
        row4.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());
        row4.getCell(6).setCellValue(businessDataVO.getNewUsers());

        // 获取第五行
        XSSFRow row5 = sheet.getRow(4);
        row5.getCell(2).setCellValue(businessDataVO.getValidOrderCount());
        row5.getCell(4).setCellValue(businessDataVO.getUnitPrice());

        // 详情用循环

        // 响应流给浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        excel.write(outputStream);

        // 关闭资源
        outputStream.close();
        excel.close();
    }
    /**
     * 销售前十，这是用 java 写
     * 可以用 sql 写如下：
     * select od.name, sum(od.number) num
     * from order_detail od
     *          left join orders o on od.order_id = o.id
     * where o.order_time between '2024-06-01 00:00:00' and '2024-06-10 00:00:00' and status = 5
     * group by od.name
     * order by num desc
     * limit 0, 10;
     */
    //@Override
    //public SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end) {
    //    LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
    //    LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
    //
    //    List<OrderEntity> orderList = orderMapper.getDataByOrderTime(beginTime, endTime, OrderEntity.COMPLETED);
    //    // 所有的订单详情数据
    //    List<OrderDetailEntity> orderDetailList = new ArrayList<>();
    //
    //    for (OrderEntity order : orderList) {
    //        List<OrderDetailEntity> orderDetails = orderDetailMapper.findByOrderId(order.getId());
    //
    //        orderDetailList.addAll(orderDetails);
    //    }
    //
    //    List<Map<String, Object>> mapList = new ArrayList<>();
    //    for (OrderDetailEntity orderDetail : orderDetailList) {
    //        // key 是商品名称，val 是商品数量
    //        Map<String, Object> map = new HashMap<>();
    //        String name = orderDetail.getName();
    //        Integer number = orderDetail.getNumber();
    //
    //        int idx = -1;
    //        // 集合里面是否存在该商品
    //        for (int i = 0; i < mapList.size(); i++) {
    //            Map<String, Object> item = mapList.get(i);
    //            if (name.equals(item.get("goods"))) {
    //                idx = i;
    //                break;
    //            }
    //        }
    //        // 不存在
    //        if (idx == -1) {
    //            // 商品名称和商品数量
    //            map.put("goods", name);
    //            map.put("number", number);
    //            mapList.add(map);
    //        } else {
    //            Map<String, Object> map1 = mapList.get(idx);
    //            Integer preNum = (Integer) map1.get("number");
    //            map1.put("number", preNum + number);
    //        }
    //    }
    //    System.out.println(mapList + "mapList");
    //
    //    // 排序，从大到小
    //    List<Map<String, Object>> list = mapList.stream()
    //        .sorted((a, b) -> (Integer) b.get("number") - (Integer) a.get("number"))
    //        .collect(Collectors.toList());
    //
    //    // top 10
    //    List<Map<String, Object>> finallyList = list.subList(0, Math.min(list.size(), 10));
    //
    //    // 最终整理为以逗号分隔的字符串
    //    String goods = finallyList.stream().map((item) -> (String) item.get("goods")).collect(Collectors.joining(","));
    //    String numbers = finallyList.stream().map((item) -> item.get("number") + "").collect(Collectors.joining(","));
    //
    //    return SalesTop10ReportVO.builder()
    //        .nameList(goods)
    //        .numberList(numbers)
    //        .build();
    //}

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
