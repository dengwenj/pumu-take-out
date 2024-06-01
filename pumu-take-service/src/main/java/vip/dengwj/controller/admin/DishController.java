package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.DishDTO;
import vip.dengwj.dto.DishQueryDTO;
import vip.dengwj.result.Result;
import vip.dengwj.service.DishService;
import vip.dengwj.vo.DishVo;
import vip.dengwj.vo.PageVO;

@RestController
@Api(tags = "菜品接口")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @ApiOperation("查询菜品")
    @GetMapping("/page")
    public Result<PageVO<DishVo>> page(DishQueryDTO dishQueryDTO) {
        PageVO<DishVo> page = dishService.page(dishQueryDTO);
        return Result.success(page);
    }

    @ApiOperation("删除菜品")
    @DeleteMapping
    public Result delete(@RequestParam String ids) {
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @ApiOperation("根据菜品 id 获取数据")
    @GetMapping("/{id}")
    public Result<DishDTO> findById(@PathVariable Long id) {
        DishDTO data = dishService.findById(id);
        return Result.success(data);
    }

    @ApiOperation("编辑菜品")
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        return Result.success();
    }
}
