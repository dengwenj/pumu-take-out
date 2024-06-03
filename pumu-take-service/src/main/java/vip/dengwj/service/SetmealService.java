package vip.dengwj.service;

import vip.dengwj.dto.SetmealDTO;
import vip.dengwj.dto.SetmealQueryDTO;
import vip.dengwj.entity.SetmealEntity;
import vip.dengwj.vo.PageVO;
import vip.dengwj.vo.SetmealDishVO;
import vip.dengwj.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    void save(SetmealDTO setmealDTO);

    // 分页查询
    PageVO<SetmealVO> page(SetmealQueryDTO setmealQueryDTO);

    // 批量删除套餐
    void deleteBatch(String ids);

    // 更新套餐
    void update(SetmealDTO setmealDTO);

    // 根据 id 获取套餐
    SetmealDTO getSetmealById(Long id);

    // 根据分类 id 查询套餐
    List<SetmealEntity> getSetmealListByCategoryId(Long categoryId);

    // 根据套餐 id 查询包含的菜品
    List<SetmealDishVO> getDishLisSetmealId(Long setmealId);
}
