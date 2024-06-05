package vip.dengwj.service;

import vip.dengwj.dto.OrderSubmitDTO;
import vip.dengwj.vo.OrderSubmitVO;

public interface OrderService {
    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO);
}
