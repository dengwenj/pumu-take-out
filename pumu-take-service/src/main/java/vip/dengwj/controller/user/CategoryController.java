package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.entity.CategoryEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.CategoryService;

import java.util.List;

@RestController("userCategoryController")
@Api(tags = "c 端分类接口")
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询分类")
    @GetMapping("/list")
    public Result<List<CategoryEntity>> list(Integer type) {
        List<CategoryEntity> list = categoryService.getListByType(type);
        return Result.success(list);
    }
}
