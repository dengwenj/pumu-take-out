package vip.dengwj.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.entity.AddressBookEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.AddressBookService;

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
}
