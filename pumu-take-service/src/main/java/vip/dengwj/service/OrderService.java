package vip.dengwj.service;

import vip.dengwj.dto.OrderSubmitDTO;
import vip.dengwj.dto.OrdersPaymentDTO;
import vip.dengwj.vo.OrderPaymentVO;
import vip.dengwj.vo.OrderSubmitVO;

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
}
