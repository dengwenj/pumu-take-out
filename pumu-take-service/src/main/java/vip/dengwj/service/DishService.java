package vip.dengwj.service;

import vip.dengwj.dto.DishDTO;
import vip.dengwj.dto.DishQueryDTO;
import vip.dengwj.entity.DishEntity;
import vip.dengwj.vo.DishVo;
import vip.dengwj.vo.PageVO;

import java.util.List;

public interface DishService {
    // 新增菜品
    void saveWithFlavor(DishDTO dishDTO);

    // 查询菜品
    PageVO<DishVo> page(DishQueryDTO dishQueryDTO);

    // 删除菜品
    void deleteBatch(String ids);

    DishDTO findById(Long id);

    void update(DishDTO dishDTO);

    void startOrStop(Integer status, Long id);

    // 根据分类 id 查询菜品
    List<DishEntity> getDishByCategoryId(Long categoryId);
}
