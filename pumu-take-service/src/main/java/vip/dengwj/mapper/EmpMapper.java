package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.dto.EmpLoginDTO;
import vip.dengwj.entity.EmpEntity;

@Mapper
public interface EmpMapper {
    // 根据用户名和密码获取员工
    EmpEntity getEmpByUsernameAndPassword(EmpLoginDTO empLoginDTO);
}
