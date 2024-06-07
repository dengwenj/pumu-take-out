package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.dto.GoodsSalesDTO;
import vip.dengwj.entity.OrderDetailEntity;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insertBatch(List<OrderDetailEntity> list);

    // 根据订单 id 获取订单详情
    List<OrderDetailEntity> findByOrderId(Long orderId);

    List<GoodsSalesDTO> getGoodsSalesTop10(LocalDateTime begin, LocalDateTime end, Integer status);
}
