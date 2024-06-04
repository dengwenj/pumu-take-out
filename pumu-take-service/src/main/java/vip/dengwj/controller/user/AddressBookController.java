package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.entity.AddressBookEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.AddressBookService;

import java.util.List;

@RestController
@Api(tags = "地址薄相关接口")
@RequestMapping("/user/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result insert(@RequestBody AddressBookEntity addressBookEntity) {
        addressBookService.insert(addressBookEntity);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("获取全部地址")
    public Result<List<AddressBookEntity>> list() {
        List<AddressBookEntity> list = addressBookService.list();
        return Result.success(list);
    }
}
