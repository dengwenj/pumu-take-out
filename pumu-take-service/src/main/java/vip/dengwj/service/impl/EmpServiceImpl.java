package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import vip.dengwj.constant.PasswordConstant;
import vip.dengwj.constant.StatusConstant;
import vip.dengwj.context.BaseContext;
import vip.dengwj.dto.EmpDTO;
import vip.dengwj.dto.EmpQueryDTO;
import vip.dengwj.entity.EmpEntity;
import vip.dengwj.mapper.EmpMapper;
import vip.dengwj.service.EmpService;
import vip.dengwj.utils.ObjectToMapUtils;
import vip.dengwj.vo.PageVO;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    /**
     * 新增员工
     * @param empDTO
     */
    @Override
    public void save(EmpDTO empDTO) {
        EmpEntity empEntity = new EmpEntity();
        // 对象拷贝
        BeanUtils.copyProperties(empDTO, empEntity);

        String password = DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        empEntity.setPassword(password);

        empEntity.setStatus(StatusConstant.ENABLE);

        empMapper.saveEmp(empEntity);

        // 移除
        BaseContext.remove();
    }

    /**
     * 查询员工
     */
    @Override
    public PageVO<EmpEntity> page(EmpQueryDTO empQueryDTO) throws IllegalAccessException {
        Integer page = empQueryDTO.getPage();
        Integer pageSize = empQueryDTO.getPageSize();
        int start = (page - 1) * pageSize;

        Map<String, Object> empQueryMap = ObjectToMapUtils.objectToMap(empQueryDTO);
        empQueryMap.remove("page");
        empQueryMap.remove("pageSize");
        Integer count = empMapper.count(empQueryMap);
        List<EmpEntity> empList = empMapper.page(empQueryMap, start, pageSize);

        PageVO<EmpEntity> pageVO = new PageVO<>();
        pageVO.setTotal(count);
        pageVO.setRecords(empList);
        return pageVO;
    }

    /**
     * 启用禁用员工
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        if (Objects.equals(status, StatusConstant.ENABLE)) {
            status = StatusConstant.DISABLE;
        } else {
            status = StatusConstant.ENABLE;
        }
        EmpEntity empEntity = EmpEntity.builder()
            .id(id)
            .status(status)
            .build();

        empMapper.update(empEntity);
    }

    /**
     * 根据 id 获取员工
     * @param id
     * @return
     */
    @Override
    public EmpEntity findById(Long id) {
        return empMapper.getEmpById(id);
    }

    /**
     * 根据 id 编辑员工
     * @param empDTO
     */
    @Override
    public void updateById(EmpDTO empDTO) {
        EmpEntity empEntity = new EmpEntity();
        BeanUtils.copyProperties(empDTO, empEntity);

        empMapper.update(empEntity);
    }
}
