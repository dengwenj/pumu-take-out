package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.CategoryDTO;
import vip.dengwj.dto.CategoryQueryDTo;
import vip.dengwj.entity.CategoryEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.CategoryService;
import vip.dengwj.vo.PageVO;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "分类管理")
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询分类")
    public Result<PageVO<CategoryEntity>> page(CategoryQueryDTo categoryQueryDTo) throws IllegalAccessException {
        PageVO<CategoryEntity> data = categoryService.page(categoryQueryDTo);
        return Result.success(data);
    }

    @PostMapping("/update")
    @ApiOperation("更新分类")
    public Result update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/{status}/{id}")
    @ApiOperation("启用禁用分类")
    public Result startOrStop(@PathVariable Integer status, @PathVariable Long id ) {
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/type")
    @ApiOperation("根据类型查询分类")
    public Result<List<CategoryEntity>> getListByType(Integer type) {
        System.out.println("type:" + type);
        List<CategoryEntity> list = categoryService.getListByType(type);
        return Result.success(list);
    }
}
