package vip.dengwj.service;

import vip.dengwj.dto.UserLoginDTO;
import vip.dengwj.entity.UserEntity;

public interface UserService {
    UserEntity wxLogin(UserLoginDTO userLoginDTO);
}
