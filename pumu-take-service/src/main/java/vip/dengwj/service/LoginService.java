package vip.dengwj.service;

import vip.dengwj.dto.EmpLoginDTO;
import vip.dengwj.vo.EmpLoginVO;

public interface LoginService {
    // 用户登录，返回 token
    EmpLoginVO login(EmpLoginDTO empLoginDTO);
}
