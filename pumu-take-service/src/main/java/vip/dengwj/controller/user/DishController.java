package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.dto.DishDTO;
import vip.dengwj.result.Result;
import vip.dengwj.service.DishService;

import java.util.List;

@RestController("userDishController")
@Api("c端菜品相关接口")
@RequestMapping("/user/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("根据分类 id 查询菜品")
    @GetMapping("/list")
    public Result<List<DishDTO>> getDishByCategoryId(Long categoryId) {
        List<DishDTO> data = dishService.getDishDTOByCategoryId(categoryId);
        return Result.success(data);
    }
}
