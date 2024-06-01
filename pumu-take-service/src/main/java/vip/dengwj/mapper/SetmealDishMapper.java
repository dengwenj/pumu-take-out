package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.SetmealDishEntity;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    // 根据菜品 id 获取套餐 id 数据
    List<Long> getSetmealIdsByDishIds(String dishIds);

    // 批量新增套餐菜品
    void insertBatch(List<SetmealDishEntity> setmealDishEntities);
}