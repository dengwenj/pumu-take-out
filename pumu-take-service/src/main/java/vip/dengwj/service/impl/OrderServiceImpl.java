package vip.dengwj.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.context.BaseContext;
import vip.dengwj.dto.OrderSubmitDTO;
import vip.dengwj.dto.OrdersPaymentDTO;
import vip.dengwj.entity.*;
import vip.dengwj.exception.BaseException;
import vip.dengwj.mapper.*;
import vip.dengwj.service.OrderService;
import vip.dengwj.utils.WeChatPayUtil;
import vip.dengwj.vo.OrderPaymentVO;
import vip.dengwj.vo.OrderSubmitVO;
import vip.dengwj.vo.PageVO;

import java.math.BigDecimal;
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
}
