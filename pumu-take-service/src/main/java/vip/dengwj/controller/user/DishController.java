package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.dto.DishDTO;
import vip.dengwj.result.Result;
import vip.dengwj.service.DishService;

import java.util.List;

@RestController("userDishController")
@Api(tags = "c端菜品相关接口")
@RequestMapping("/user/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate<String, List<DishDTO>> redisTemplate;

    @ApiOperation("根据分类 id 查询菜品")
    @GetMapping("/list")
    public Result<List<DishDTO>> getDishByCategoryId(Long categoryId) {
        String key = "dish_" + categoryId;
        // 先从 redis 里面查，有缓存从 redis 里拿
        List<DishDTO> dishDTOS = redisTemplate.opsForValue().get(key);
        if (dishDTOS != null && !dishDTOS.isEmpty()) {
            return Result.success(dishDTOS);
        }

        // redis 没有从数据库里取，然后存入到 redis
        List<DishDTO> data = dishService.getDishDTOByCategoryId(categoryId);
        redisTemplate.opsForValue().set(key, data);
        return Result.success(data);
    }
}
