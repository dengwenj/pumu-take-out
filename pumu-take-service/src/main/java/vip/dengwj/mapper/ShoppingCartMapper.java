package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.ShoppingCartEntity;

@Mapper
public interface ShoppingCartMapper {
    // 添加购物车
    void insert(ShoppingCartEntity shoppingCartEntity);

    // 更新购物车
    void update(ShoppingCartEntity shoppingCartEntity);

    // 获取商品
    ShoppingCartEntity getShopping(ShoppingCartEntity shoppingCartEntity);
}
