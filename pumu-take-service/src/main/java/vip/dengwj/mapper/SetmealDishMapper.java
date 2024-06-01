package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    // 根据菜品 id 获取套餐 id 数据
    List<Long> getSetmealIdsByDishIds(String dishIds);
}
