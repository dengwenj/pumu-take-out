package vip.dengwj.service;

import vip.dengwj.dto.DishDTO;

public interface DishService {
    // 新增菜品
    void saveWithFlavor(DishDTO dishDTO);
}
