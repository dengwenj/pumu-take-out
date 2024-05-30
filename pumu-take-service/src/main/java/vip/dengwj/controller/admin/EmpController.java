package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.dengwj.dto.EmpDTO;
import vip.dengwj.dto.EmpQueryDTO;
import vip.dengwj.entity.EmpEntity;
import vip.dengwj.result.Result;
import vip.dengwj.service.EmpService;
import vip.dengwj.vo.PageVO;

@Slf4j
@RestController
@Api(tags = "员工管理")
@RequestMapping("/admin/employee")
public class EmpController {
    @Autowired
    private EmpService empService;

    @PostMapping
    @ApiOperation("添加员工")
    public Result save(@RequestBody EmpDTO empDTO) {
        System.out.println(empDTO.toString());
        empService.save(empDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("查询员工")
    public Result<PageVO<EmpEntity>> page(EmpQueryDTO empQueryDTO) throws IllegalAccessException {
        PageVO<EmpEntity> data = empService.page(empQueryDTO);
        return Result.success(data);
    }
}
