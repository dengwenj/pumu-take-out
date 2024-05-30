package vip.dengwj.service;

import vip.dengwj.dto.EmpDTO;
import vip.dengwj.dto.EmpQueryDTO;
import vip.dengwj.entity.EmpEntity;
import vip.dengwj.vo.PageVO;

import java.util.List;

public interface EmpService {
    // 新增员工
    void save(EmpDTO empDTO);

    // 查询员工
    PageVO<EmpEntity> page(EmpQueryDTO empQueryDTO) throws IllegalAccessException;
}
