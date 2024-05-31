package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.CategoryEntity;

@Mapper
public interface CategoryMapper {
    void save(CategoryEntity categoryEntity);
}
