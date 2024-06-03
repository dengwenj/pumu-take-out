package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.result.Result;

@RestController("userShopController")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
public class ShopController {
    private static final String SHOP_STATUS = "shop_status";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("用户端获取营业状态")
    public Result<Integer> getShopS() {
        Integer shop = (Integer) redisTemplate.opsForValue().get(SHOP_STATUS);
        return Result.success(shop);
    }
}
