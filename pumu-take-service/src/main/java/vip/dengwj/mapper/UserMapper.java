package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.UserEntity;

@Mapper
public interface UserMapper {
    // 新增
    void inertUser(UserEntity userEntity);

    // 根据 openid 查询用户
    UserEntity getUserByOpenid(String openid);

    UserEntity getById(Long userId);
}
