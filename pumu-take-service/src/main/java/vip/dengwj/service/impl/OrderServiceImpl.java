package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.context.BaseContext;
import vip.dengwj.dto.OrderSubmitDTO;
import vip.dengwj.entity.AddressBookEntity;
import vip.dengwj.entity.OrderDetailEntity;
import vip.dengwj.entity.OrderEntity;
import vip.dengwj.entity.ShoppingCartEntity;
import vip.dengwj.exception.BaseException;
import vip.dengwj.mapper.AddressBookMapper;
import vip.dengwj.mapper.OrderDetailMapper;
import vip.dengwj.mapper.OrderMapper;
import vip.dengwj.mapper.ShoppingCartMapper;
import vip.dengwj.service.OrderService;
import vip.dengwj.vo.OrderSubmitVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * 提交订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO) {
        Long userId = BaseContext.get();

        // 处理各种业务异常（地址簿为空、购物车数据为空）
        AddressBookEntity addressBook = addressBookMapper.findById(orderSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new BaseException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        List<ShoppingCartEntity> list = shoppingCartMapper.listByUserId(userId);
        if (list == null || list.isEmpty()) {
            throw new BaseException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 向订单表插入数据
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderSubmitDTO, orderEntity);
        orderEntity.setOrderTime(LocalDateTime.now());
        orderEntity.setPayStatus(OrderEntity.UN_PAID);
        orderEntity.setStatus(OrderEntity.PENDING_PAYMENT);
        orderEntity.setNumber(String.valueOf(System.currentTimeMillis()));
        orderEntity.setPhone(addressBook.getPhone());
        orderEntity.setConsignee(addressBook.getConsignee());
        orderEntity.setUserId(userId);
        orderMapper.inset(orderEntity);

        // 向订单明细表批量插入数据
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();
        for (ShoppingCartEntity shoppingCart : list) {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            BeanUtils.copyProperties(shoppingCart, orderDetailEntity);
            orderDetailEntity.setOrderId(orderEntity.getId());
            orderDetailList.add(orderDetailEntity);
        }
        orderDetailMapper.insertBatch(orderDetailList);

        // 清空当前用户的购物车数据
        shoppingCartMapper.delete(ShoppingCartEntity.builder().userId(userId).build());

        // 返回结果
        return OrderSubmitVO.builder()
            .id(orderEntity.getId())
            .orderNumber(orderEntity.getNumber())
            .orderAmount(orderEntity.getAmount())
            .orderTime(orderEntity.getOrderTime())
            .build();
    }
}
