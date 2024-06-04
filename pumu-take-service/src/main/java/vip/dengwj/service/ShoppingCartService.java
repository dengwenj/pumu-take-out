package vip.dengwj.service;

import vip.dengwj.dto.ShoppingCartDTO;
import vip.dengwj.entity.ShoppingCartEntity;

import java.util.List;

public interface ShoppingCartService {
    // 添加购物车
    void insert(ShoppingCartDTO shoppingCartDTO);

    // 查询购物车
    List<ShoppingCartEntity> list();

    void clear();

    // 减少商品
    void sub(ShoppingCartDTO shoppingCartDTO);
}
