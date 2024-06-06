package vip.dengwj.service;

import vip.dengwj.dto.OrderQueryDTO;
import vip.dengwj.dto.OrderSubmitDTO;
import vip.dengwj.dto.OrdersPaymentDTO;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.entity.OrderQueryEntity;
import vip.dengwj.vo.OrderPaymentVO;
import vip.dengwj.vo.OrderSubmitVO;
import vip.dengwj.vo.PageVO;

public interface OrderService {
    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    PageVO<OrderEntity> page(Integer page, Integer pageSize, Integer status);

    OrderEntity orderDetail(Long id);

    void cancelOrder(Long id);

    // 再来一单
    void repetition(Long id);

    // pc 端分页查询
    PageVO<OrderQueryEntity> adminPage(OrderQueryDTO orderQueryDTO);
}
