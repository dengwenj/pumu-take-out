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
}
