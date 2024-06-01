package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.DishFlavorEntity;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    // foreach 批量添加
    void insertBatch(List<DishFlavorEntity>  dishFlavorEntities);
}
