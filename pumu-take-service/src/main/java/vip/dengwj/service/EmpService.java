package vip.dengwj.service;

import org.springframework.stereotype.Service;
import vip.dengwj.dto.EmpDTO;

public interface EmpService {
    // 新增员工
    void save(EmpDTO empDTO);
}
