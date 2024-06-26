package vip.dengwj.service;

import vip.dengwj.vo.OrderReportVO;
import vip.dengwj.vo.SalesTop10ReportVO;
import vip.dengwj.vo.TurnoverReportVO;
import vip.dengwj.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end);

    UserReportVO getUserReport(LocalDate begin, LocalDate end);

    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end);

    void export(HttpServletResponse response) throws IOException;
}
