package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.SetmealDTO;
import vip.dengwj.dto.SetmealQueryDTO;
import vip.dengwj.result.Result;
import vip.dengwj.service.SetmealService;
import vip.dengwj.vo.PageVO;
import vip.dengwj.vo.SetmealVO;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @ApiOperation("新增套餐")
    @PostMapping
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setmealService.save(setmealDTO);
        return Result.success();
    }

    @ApiOperation("分页查询套餐")
    @GetMapping("/page")
    public Result<PageVO<SetmealVO>> page(SetmealQueryDTO setmealQueryDTO) {
        PageVO<SetmealVO> page = setmealService.page(setmealQueryDTO);
        return Result.success(page);
    }

    @ApiOperation("删除套餐")
    @DeleteMapping
    public Result deleteBatch(String ids) {
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @ApiOperation("根据 id 查询套餐")
    @GetMapping("/{id}")
    public Result getSetmealById(@PathVariable Long id) {
        SetmealDTO data = setmealService.getSetmealById(id);
        return Result.success(data);
    }

    @ApiOperation("更新套餐")
    @PutMapping
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }
}
