package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import vip.dengwj.dto.EmpLoginDTO;
import vip.dengwj.entity.EmpEntity;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    // 根据用户名和密码获取员工
    EmpEntity getEmpByUsernameAndPassword(EmpLoginDTO empLoginDTO);

    // 新增员工
    void saveEmp(EmpEntity empEntity);

    // 查询员工
    // 单个参数 empQueryMap 要加 @param(), 多个可以不加
    List<EmpEntity> page(Map<String, Object> empQueryMap, int currentPage, int pageSize);

    // 获取总条数
    Integer count(@Param("empQueryMap") Map<String, Object> empQueryMap);
}
