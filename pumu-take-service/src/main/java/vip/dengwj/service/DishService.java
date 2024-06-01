package vip.dengwj.service;

import vip.dengwj.dto.DishDTO;
import vip.dengwj.dto.DishQueryDTO;
import vip.dengwj.vo.DishVo;
import vip.dengwj.vo.PageVO;

public interface DishService {
    // 新增菜品
    void saveWithFlavor(DishDTO dishDTO);

    // 查询菜品
    PageVO<DishVo> page(DishQueryDTO dishQueryDTO);

    // 删除菜品
    void deleteBatch(String ids);

    DishDTO findById(Long id);
}
