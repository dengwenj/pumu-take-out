package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.ShoppingCartDTO;
import vip.dengwj.entity.ShoppingCartEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.ShoppingCartService;

import java.util.List;

@RestController
@Api(tags = "购物车相关接口")
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result insert(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.insert(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询购物车")
    public Result<List<ShoppingCartEntity>> list() {
        List<ShoppingCartEntity> list = shoppingCartService.list();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clear() {
        shoppingCartService.clear();
        return Result.success();
    }
}
