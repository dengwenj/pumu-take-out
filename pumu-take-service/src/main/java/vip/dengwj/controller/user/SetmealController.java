package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.entity.SetmealEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.SetmealService;

import java.util.List;

@RestController("userSetmealController")
@Api("c端套餐相关接口")
@RequestMapping("/user/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/list")
    @ApiOperation("根据分类 id 查询套餐")
    public Result getSetmealByCategoryId(Long categoryId) {
        List<SetmealEntity> data = setmealService.getSetmealListByCategoryId(categoryId);
        return Result.success(data);
    }
}
