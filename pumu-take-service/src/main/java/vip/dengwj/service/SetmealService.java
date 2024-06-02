package vip.dengwj.service;

import vip.dengwj.dto.SetmealDTO;
import vip.dengwj.dto.SetmealQueryDTO;
import vip.dengwj.vo.PageVO;
import vip.dengwj.vo.SetmealVO;

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
}
