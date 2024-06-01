package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.DishFlavorEntity;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    // foreach 批量添加
    void insertBatch(List<DishFlavorEntity>  dishFlavorEntities);

    // 通过菜品 id 删除 菜品口味
    void deleteByDishId(String ids);

    // 通过菜品 id 获取菜品口味
    List<DishFlavorEntity> getListByDishId(Long dishId);
}
