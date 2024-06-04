package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.AddressBookDTO;
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

    @GetMapping("/{id}")
    @ApiOperation("根据 id 查询地址")
    public Result<AddressBookEntity> findById(@PathVariable Long id) {
        AddressBookEntity data = addressBookService.findById(id);
        return Result.success(data);
    }

    @PutMapping
    @ApiOperation("根据 id 修改地址")
    public Result update(@RequestBody AddressBookEntity addressBookEntity) {
        addressBookService.update(addressBookEntity);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("根据 id 删除地址")
    public Result delete(Long id) {
        addressBookService.delete(id);
        return Result.success();
    }

    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBookEntity> findDefault() {
        AddressBookEntity aDefault = addressBookService.findDefault();
        return Result.success(aDefault);
    }

    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result updateDefault(@RequestBody AddressBookDTO addressBookDTO) {
        addressBookService.updateDefault(addressBookDTO.getId());
        return Result.success();
    }
}
