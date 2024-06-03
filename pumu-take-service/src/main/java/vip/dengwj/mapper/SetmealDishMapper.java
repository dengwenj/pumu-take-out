package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.SetmealDishEntity;
import vip.dengwj.vo.SetmealDishVO;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    // 根据菜品 id 获取套餐 id 数据
    List<Long> getSetmealIdsByDishIds(String dishIds);

    // 批量新增套餐菜品
    void insertBatch(List<SetmealDishEntity> setmealDishEntities);

    // 根据套餐 id 批量删除数据
    void deleteBySetmealIds(String SetmealIds);

    // 根据套餐 id 获取数据
    List<SetmealDishEntity> findBySetmealId(Long setmealId);

    // 根据套餐 id 获取菜品包括菜品图片
    List<SetmealDishVO> getSetmealDishVOBySetmealId(Long setmealId);
}
