package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.annotation.AutoFill;
import vip.dengwj.dto.CategoryQueryDTo;
import vip.dengwj.entity.CategoryEntity;
import vip.dengwj.enun.InsertOrUpdate;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @AutoFill(InsertOrUpdate.INSERT)
    void save(CategoryEntity categoryEntity);

    List<CategoryEntity> page(CategoryQueryDTo categoryQueryDTo);

    Integer count(CategoryQueryDTo categoryQueryDTo);

    @AutoFill(InsertOrUpdate.UPDATE)
    void update(CategoryEntity categoryEntity);

    List<CategoryEntity> getListByType(Integer type);

    void delete(Long id);
}
