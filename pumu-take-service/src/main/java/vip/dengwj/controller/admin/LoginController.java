package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.dengwj.dto.EmpLoginDTO;
import vip.dengwj.result.Result;
import vip.dengwj.service.LoginService;
import vip.dengwj.vo.EmpLoginVO;

@Slf4j
@RestController
@Api(tags = "员工登录模块")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/admin/login")
    @ApiOperation("登录")
    public Result<EmpLoginVO> login(@RequestBody EmpLoginDTO empLoginDTO) {
        log.info("用户名，{}", empLoginDTO);
        EmpLoginVO data = loginService.login(empLoginDTO);
        return Result.success(data);
    }
}
