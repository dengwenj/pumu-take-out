package vip.dengwj.service;

import vip.dengwj.vo.OrderReportVO;
import vip.dengwj.vo.TurnoverReportVO;
import vip.dengwj.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end);

    UserReportVO getUserReport(LocalDate begin, LocalDate end);

    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);
}
