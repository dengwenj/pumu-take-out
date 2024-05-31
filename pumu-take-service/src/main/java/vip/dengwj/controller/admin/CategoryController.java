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
}
