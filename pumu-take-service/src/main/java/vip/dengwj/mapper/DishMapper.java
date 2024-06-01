package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.annotation.AutoFill;
import vip.dengwj.entity.DishEntity;
import vip.dengwj.enun.InsertOrUpdate;

@Mapper
public interface DishMapper {
    /**
     * 新增接口
     */
    @AutoFill(InsertOrUpdate.INSERT)
    // 也可以在 xml 中写，这里就注释掉了
    //@Options(useGeneratedKeys = true, keyProperty = "id") // 返回主键，会往 dishEntity 对象中赋值 id 属性
    void save(DishEntity dishEntity);
}
