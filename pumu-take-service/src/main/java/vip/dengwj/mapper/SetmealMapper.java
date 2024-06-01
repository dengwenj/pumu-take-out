package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.annotation.AutoFill;
import vip.dengwj.entity.SetmealEntity;
import vip.dengwj.enun.InsertOrUpdate;

@Mapper
public interface SetmealMapper {
    @AutoFill(InsertOrUpdate.INSERT)
    void insert(SetmealEntity setmealEntity);
}
