package vip.dengwj.service;

import vip.dengwj.dto.EmpDTO;
import vip.dengwj.dto.EmpQueryDTO;
import vip.dengwj.entity.EmpEntity;
import vip.dengwj.vo.PageVO;

public interface EmpService {
    // 新增员工
    void save(EmpDTO empDTO);

    // 查询员工
    PageVO<EmpEntity> page(EmpQueryDTO empQueryDTO) throws IllegalAccessException;

    // 启用禁用员工
    void startOrStop(Integer status, Long id);

    // 根据 id 获取员工
    EmpEntity findById(Long id);

    // 编辑员工
    void updateById(EmpDTO empDTO);
}
