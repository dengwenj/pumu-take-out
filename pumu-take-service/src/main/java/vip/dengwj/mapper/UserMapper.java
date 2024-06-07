package vip.dengwj.mapper;

import org.apache.ibatis.annotations.Mapper;
import vip.dengwj.entity.UserEntity;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    // 新增
    void inertUser(UserEntity userEntity);

    // 根据 openid 查询用户
    UserEntity getUserByOpenid(String openid);

    UserEntity getById(Long userId);

    // 根据日期统计新增人数
    Long getCountByCreateTime(LocalDateTime begin, LocalDateTime end);

    // 根据时间统计全部人数
    Long getCountAll(LocalDateTime end);
}
