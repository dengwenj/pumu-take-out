package vip.dengwj.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.context.BaseContext;
import vip.dengwj.dto.*;
import vip.dengwj.entity.*;
import vip.dengwj.exception.BaseException;
import vip.dengwj.mapper.*;
import vip.dengwj.service.OrderService;
import vip.dengwj.utils.WeChatPayUtil;
import vip.dengwj.vo.OrderPaymentVO;
import vip.dengwj.vo.OrderStatusVO;
import vip.dengwj.vo.OrderSubmitVO;
import vip.dengwj.vo.PageVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

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

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = BaseContext.get();
        UserEntity user = userMapper.getById(userId);

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
            ordersPaymentDTO.getOrderNumber(), //商户订单号
            new BigDecimal("0.01"), //支付金额，单位 元
            "苍穹外卖订单", //商品描述
            user.getOpenid() //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new BaseException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        OrderEntity ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        OrderEntity orders = OrderEntity.builder()
            .id(ordersDB.getId())
            .status(OrderEntity.TO_BE_CONFIRMED)
            .payStatus(OrderEntity.PAID)
            .checkoutTime(LocalDateTime.now())
            .build();

        orderMapper.update(orders);
    }

    /**
     * 分页查询
     */
    @Override
    public PageVO<OrderEntity> page(Integer page, Integer pageSize, Integer status) {
        int start = (page - 1) * pageSize;
        List<OrderEntity> list = orderMapper.page(start, pageSize, status);

        // 总条数
        Integer count = orderMapper.count(status);

        // 根据订单号获取订单详情
        for (OrderEntity order : list) {
             order.setOrderDetailList(orderDetailMapper.findByOrderId(order.getId()));
        }
        return new PageVO<>(count, list);
    }

    /**
     * 查询订单详情
     */
    @Override
    public OrderEntity orderDetail(Long id) {
        OrderEntity order = orderMapper.getOrderById(id);

        List<OrderDetailEntity> orderDetail = orderDetailMapper.findByOrderId(order.getId());

        order.setOrderDetailList(orderDetail);
        return order;
    }

    /**
     * 取消订单
     */
    @Override
    public void cancelOrder(Long id) {
        OrderEntity order = orderMapper.getOrderById(id);

        // 待支付和待接单状态下，用户可直接取消订单
        if (
            Objects.equals(order.getStatus(), OrderEntity.PENDING_PAYMENT)
                || Objects.equals(order.getStatus(), OrderEntity.TO_BE_CONFIRMED)
        ) {
            order.setStatus(OrderEntity.CANCELLED);
            orderMapper.update(order);

            // 如果在待接单状态下取消订单，需要给用户退款
            if (Objects.equals(order.getStatus(), OrderEntity.TO_BE_CONFIRMED)) {
                System.out.println("接单状态下取消订单，需要给用户退款");
            }
        }

        // 商家已接单状态下，用户取消订单需电话沟通商家。派送中状态下，用户取消订单需电话沟通商家
        if (
            order.getStatus().equals(OrderEntity.CONFIRMED)
                || order.getStatus().equals(OrderEntity.DELIVERY_IN_PROGRESS)
        ) {
            throw new BaseException("已接单或派送中取消订单，需电话沟通商家");
        }
    }

    /**
     * 再来一单
     */
    @Override
    public void repetition(Long id) {
        // 再来一单就是将原订单中的商品重新加入到购物车中
        List<OrderDetailEntity> orderDetailList = orderDetailMapper.findByOrderId(id);

        List<ShoppingCartEntity> shoppingCartList = new ArrayList<>();
        for (OrderDetailEntity orderDetail : orderDetailList) {
            ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
            BeanUtils.copyProperties(orderDetail, shoppingCart);
            shoppingCart.setUserId(BaseContext.get());
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartList.add(shoppingCart);
        }
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    /**
     * pc 端查询
     */
    @Override
    public PageVO<OrderQueryEntity> adminPage(OrderQueryDTO orderQueryDTO) {
        Integer page = orderQueryDTO.getPage();
        Integer pageSize = orderQueryDTO.getPageSize();
        int start = (page - 1) * pageSize;
        orderQueryDTO.setPage(start);

        List<OrderQueryEntity> orderList = orderMapper.adminPage(orderQueryDTO);

        Integer count = orderMapper.adminCount(orderQueryDTO);

        return new PageVO<>(count, orderList);
    }

    /**
     * 各个状态的订单数量统计
     */
    @Override
    public OrderStatusVO statistics() {
        // 待派送
        Integer confirmed = orderMapper.confirmed(OrderEntity.CONFIRMED);
        // 派送中
        Integer deliveryInProgress = orderMapper.deliveryInProgress(OrderEntity.DELIVERY_IN_PROGRESS);
        // 待接单
        Integer toBeConfirmed = orderMapper.toBeConfirmed(OrderEntity.TO_BE_CONFIRMED);
        return new OrderStatusVO(confirmed, deliveryInProgress, toBeConfirmed);
    }

    /**
     * 接单
     */
    @Override
    public void confirm(Long id) {
        // 商家接单其实就是将订单的状态修改为“已接单”
        OrderEntity order = OrderEntity.builder()
            .id(id)
            .status(OrderEntity.CONFIRMED)
            .build();
        orderMapper.update(order);
    }

    /**
     * 拒单
     */
    @Override
    public void rejection(OrderRejectionDTO orderRejectionDTO) {
        // 只有订单处于“待接单”状态时可以执行拒单操作
        OrderEntity order = orderMapper.getOrderById(orderRejectionDTO.getId());
        if (!order.getStatus().equals(OrderEntity.TO_BE_CONFIRMED)) {
            throw new BaseException("只有订单处于“待接单”状态时可以执行拒单操作");
        }

        // 商家拒单其实就是将订单状态修改为“已取消”，商家拒单时需要指定拒单原因
        order.setStatus(OrderEntity.CANCELLED);
        order.setRejectionReason(orderRejectionDTO.getRejectionReason());

        // 商家拒单时，如果用户已经完成了支付，需要为用户退款
        if (order.getPayStatus().equals(OrderEntity.PAID)) {
            order.setPayStatus(OrderEntity.REFUND);
            System.out.println("退款给用户");
        }

        orderMapper.update(order);
    }

    /**
     * 取消订单
     */
    @Override
    public void adminCancel(OrderCancelDTO orderCancelDTO) {
        OrderEntity order = orderMapper.getOrderById(orderCancelDTO.getId());

        // 取消订单其实就是将订单状态修改为“已取消”
        // 商家取消订单时需要指定取消原因
        order.setStatus(OrderEntity.CANCELLED);
        order.setCancelReason(orderCancelDTO.getCancelReason());
        order.setCancelTime(LocalDateTime.now());
        // 商家取消订单时，如果用户已经完成了支付，需要为用户退款
        if (order.getPayStatus().equals(OrderEntity.PAID)) {
            order.setPayStatus(OrderEntity.REFUND);
            System.out.println("退款给用户");
        }
        orderMapper.update(order);
    }

    /**
     * 派送订单
     */
    @Override
    public void adminDelivery(Long id) {
        OrderEntity order = orderMapper.getOrderById(id);
        if (!order.getStatus().equals(OrderEntity.CONFIRMED)) {
            throw new BaseException("只有状态为“待派送”的订单可以执行派送订单操作");
        }

        // 派送订单其实就是将订单状态修改为“派送中”
        order.setStatus(OrderEntity.DELIVERY_IN_PROGRESS);
        order.setDeliveryTime(LocalDateTime.now());
        orderMapper.update(order);
    }
}
