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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

        empEntity.setCreateTime(LocalDateTime.now());
        empEntity.setUpdateTime(LocalDateTime.now());

        // 获取当前线程的局部变量
        Long empId = BaseContext.get();
        empEntity.setCreateUser(empId);
        empEntity.setUpdateUser(empId);

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

        Integer count = empMapper.count(empQueryDTO);
        Map<String, Object> empQueryMap = ObjectToMapUtils.objectToMap(empQueryDTO);
        empQueryMap.remove("page");
        empQueryMap.remove("pageSize");
        List<EmpEntity> empList = empMapper.page(empQueryMap, start, pageSize);

        PageVO<EmpEntity> pageVO = new PageVO<>();
        pageVO.setTotal(count);
        pageVO.setRecords(empList);
        return pageVO;
    }
}
