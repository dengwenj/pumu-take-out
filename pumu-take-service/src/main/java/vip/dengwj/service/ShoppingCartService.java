package vip.dengwj.service;

import vip.dengwj.dto.ShoppingCartDTO;

public interface ShoppingCartService {
    // 添加购物车
    void insert(ShoppingCartDTO shoppingCartDTO);
}
