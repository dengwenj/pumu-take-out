package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.dengwj.context.BaseContext;
import vip.dengwj.dto.ShoppingCartDTO;
import vip.dengwj.entity.DishEntity;
import vip.dengwj.entity.SetmealEntity;
import vip.dengwj.entity.ShoppingCartEntity;
import vip.dengwj.mapper.DishMapper;
import vip.dengwj.mapper.SetmealMapper;
import vip.dengwj.mapper.ShoppingCartMapper;
import vip.dengwj.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     */
    @Override
    public void insert(ShoppingCartDTO shoppingCartDTO) {
        Long userId = BaseContext.get();
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCartEntity);
        shoppingCartEntity.setUserId(userId);

        ShoppingCartEntity shoppingCart = shoppingCartMapper.getShopping(shoppingCartEntity);

        // 判断当前加入到购物车中的商品是否已经存在
        if (shoppingCart != null) {
            // 如果已经存在，只需要将数量 +1
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            shoppingCartMapper.update(shoppingCart);
            return;
        }

        // 如果不存在，需要插入一条购物车数据
        Long dishId = shoppingCartDTO.getDishId();
        Long setmealId = shoppingCartDTO.getSetmealId();
        String name;
        BigDecimal amount;
        String image;
        // 说明传的菜品
        if (dishId != null) {
            DishEntity dish = dishMapper.getDisByIds(dishId + "").get(0);
            name = dish.getName();
            amount = dish.getPrice();
            image = dish.getImage();
        } else {
            // 说明传的套餐
            SetmealEntity setmeal = setmealMapper.getSetmealByIds(setmealId + "").get(0);
            name = setmeal.getName();
            amount = setmeal.getPrice();
            image = setmeal.getImage();
        }
        shoppingCartEntity.setName(name);
        shoppingCartEntity.setAmount(amount);
        shoppingCartEntity.setImage(image);
        shoppingCartEntity.setNumber(1);
        shoppingCartMapper.insert(shoppingCartEntity);
    }

    /**
     * 查询购物车
     */
    @Override
    public List<ShoppingCartEntity> list() {
        Long userId = BaseContext.get();
        return shoppingCartMapper.listByUserId(userId);
    }

    /**
     * 清空购物车
     */
    @Override
    public void clear() {
        Long userId = BaseContext.get();
        shoppingCartMapper.clear(userId);
    }
}
