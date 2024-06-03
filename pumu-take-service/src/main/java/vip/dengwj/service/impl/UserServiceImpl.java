package vip.dengwj.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.dengwj.constant.MessageConstant;
import vip.dengwj.constant.WXLoginParams;
import vip.dengwj.dto.UserLoginDTO;
import vip.dengwj.entity.UserEntity;
import vip.dengwj.exception.BaseException;
import vip.dengwj.mapper.UserMapper;
import vip.dengwj.properties.WXInfoProperties;
import vip.dengwj.service.UserService;
import vip.dengwj.utils.HttpClientUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private static final String wxLoginUrl = "https://api.weixin.qq.com/sns/jscode2session";
    private static final String OPENID = "openid";
    private static final String AUTHORIZATION_CODE = "authorization_code";

    @Autowired
    private WXInfoProperties wxInfo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity wxLogin(UserLoginDTO userLoginDTO) {
        // 获取 openid
        String openid = getOpenid(userLoginDTO.getCode());

        // openid 为空说明 code 有问题
        if (openid.trim().isEmpty()) {
            throw new BaseException(MessageConstant.LOGIN_FAILED);
        }

        // 是否为新用户
        UserEntity user = userMapper.getUserByOpenid(openid);
        // 说明有该用户
        if (user != null) {
            return user;
        }

        // 说明是新用户，进行注册
        UserEntity userEntity = UserEntity.builder()
            .openid(openid)
            .createTime(LocalDateTime.now())
            .build();
        userMapper.inertUser(userEntity);

        return userEntity;
    }

    /**
     * 调用微信登录接口，获取 openid
     */
    public String getOpenid(String code) {
        Map<String, String> params = new HashMap<>();
        params.put(WXLoginParams.APPID, wxInfo.getAppId());
        params.put(WXLoginParams.SECRET, wxInfo.getAppSecret());
        params.put(WXLoginParams.JS_CODE, code);
        params.put(WXLoginParams.GRANT_TYPE, AUTHORIZATION_CODE);

        String data = HttpClientUtil.doGet(wxLoginUrl, params);
        // 转成对象
        JsonObject resData = JsonParser.parseString(data).getAsJsonObject();

        return resData.get(OPENID).getAsString();
    }
}
