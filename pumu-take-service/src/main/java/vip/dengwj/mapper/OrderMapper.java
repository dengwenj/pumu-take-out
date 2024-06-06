package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import vip.dengwj.dto.OrderQueryDTO;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.entity.OrderQueryEntity;

import java.util.List;

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

    List<OrderEntity> page(Integer page, Integer pageSize, Integer status);

    // 获取总条数
    Integer count(Integer status);

    OrderEntity getOrderById(Long id);

    // pc 端分页查询
    List<OrderQueryEntity> adminPage(OrderQueryDTO orderQueryDTO);

    // pc 端查询总数
    Integer adminCount(OrderQueryDTO orderQueryDTO);
}
