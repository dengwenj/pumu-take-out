package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import vip.dengwj.entity.OrderEntity;

@Mapper
public interface OrderMapper {
    void inset(OrderEntity orderEntity);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    OrderEntity getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(OrderEntity orders);
}
