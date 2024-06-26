package vip.dengwj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import vip.dengwj.constant.JWTConstant;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.constant.StatusConstant;
import vip.dengwj.dto.EmpLoginDTO;
import vip.dengwj.entity.EmpEntity;
import vip.dengwj.exception.AccountLockedException;
import vip.dengwj.exception.AccountNotFoundException;
import vip.dengwj.exception.PasswordErrorException;
import vip.dengwj.mapper.EmpMapper;
import vip.dengwj.properties.JWTProperties;
import vip.dengwj.service.LoginService;
import vip.dengwj.utils.JWTUtils;
import vip.dengwj.vo.EmpLoginVO;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 控制反转
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    EmpMapper empMapper;
    @Autowired
    JWTProperties jwtProperties;

    @Override
    public EmpLoginVO login(EmpLoginDTO empLoginDTO) {
        // 用户名或密码没有
        if (empLoginDTO.getUsername() == null || empLoginDTO.getPassword() == null) {
            return null;
        }

        // 用户名不存在
        EmpEntity empUser = empMapper.getEmpByUsernameAndPassword(empLoginDTO);
        if (empUser == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 密码不正确
        // 加密后在进行对不
        String md5Password = DigestUtils.md5DigestAsHex(empLoginDTO.getPassword().getBytes());
        if (!empUser.getPassword().equals(md5Password)) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 账号被锁定
        if (Objects.equals(empUser.getStatus(), StatusConstant.DISABLE)) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        Map<String, Object> data = new HashMap<>();
        data.put(JWTConstant.EMP_ID, empUser.getId());
        data.put(JWTConstant.USERNAME, empUser.getUsername());
        data.put(JWTConstant.NAME, empUser.getName());

        //生成 token
        String jwt = JWTUtils.createJWT(
            jwtProperties.getAdminSecretKey(),
            jwtProperties.getAdminTtl(),
            data
        );

        return EmpLoginVO.builder()
            .id(empUser.getId())
            .username(empUser.getUsername())
            .name(empUser.getUsername())
            .token(jwt)
            .build();
    }
}
