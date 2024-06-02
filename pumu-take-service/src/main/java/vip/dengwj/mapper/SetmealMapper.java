package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.annotation.AutoFill;
import vip.dengwj.dto.SetmealQueryDTO;
import vip.dengwj.entity.SetmealEntity;
import vip.dengwj.enun.InsertOrUpdate;
import vip.dengwj.vo.SetmealVO;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @AutoFill(InsertOrUpdate.INSERT)
    void insert(SetmealEntity setmealEntity);

    // 分页查询
    List<SetmealVO> page(SetmealQueryDTO setmealQueryDTO);

    // 通过查询条件获取数量
    Integer count(SetmealQueryDTO setmealQueryDTO);
}
