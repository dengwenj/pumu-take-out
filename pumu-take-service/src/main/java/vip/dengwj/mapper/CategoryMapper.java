package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.dto.CategoryQueryDTo;
import vip.dengwj.entity.CategoryEntity;

import java.util.List;

@Mapper
public interface CategoryMapper {
    void save(CategoryEntity categoryEntity);

    List<CategoryEntity> page(CategoryQueryDTo categoryQueryDTo);

    Integer count(CategoryQueryDTo categoryQueryDTo);

    void update(CategoryEntity categoryEntity);
}
