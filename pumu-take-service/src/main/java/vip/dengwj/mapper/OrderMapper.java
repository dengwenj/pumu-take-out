package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.OrderEntity;

@Mapper
public interface OrderMapper {
    void inset(OrderEntity orderEntity);
}
