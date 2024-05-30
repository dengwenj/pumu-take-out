package vip.dengwj.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import vip.dengwj.constant.PasswordConstant;
import vip.dengwj.constant.StatusConstant;
import vip.dengwj.dto.EmpDTO;
import vip.dengwj.entity.EmpEntity;
import vip.dengwj.mapper.EmpMapper;
import vip.dengwj.service.EmpService;

import java.time.LocalDateTime;

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

        // TODO 后续修改用户 id
        empEntity.setCreateUser(10L);
        empEntity.setUpdateUser(10L);

        empMapper.saveEmp(empEntity);
    }
}
