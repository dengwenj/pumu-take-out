package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.constant.JWTConstant;
import vip.dengwj.dto.UserLoginDTO;
import vip.dengwj.entity.UserEntity;
import vip.dengwj.properties.JWTProperties;
import vip.dengwj.result.Result;
import vip.dengwj.service.UserService;
import vip.dengwj.utils.JWTUtils;
import vip.dengwj.vo.UserLoginVO;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "c 端用户相关接口")
@RequestMapping("/user/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        UserEntity userEntity = userService.wxLogin(userLoginDTO);

        Map<String, Object> map = new HashMap<>();
        map.put(JWTConstant.USER_ID, userEntity.getId());
        String jwt = JWTUtils.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), map);

        UserLoginVO data = UserLoginVO.builder()
            .id(userEntity.getId())
            .openid(userEntity.getOpenid())
            .token(jwt)
            .build();

        return Result.success(data);
    }
}
