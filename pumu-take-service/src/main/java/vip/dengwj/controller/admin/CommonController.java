package vip.dengwj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vip.dengwj.exception.BaseException;
import vip.dengwj.result.Result;
import vip.dengwj.service.CommonService;

import java.io.IOException;

/**
 * @date 2024/6/1 15:48
 * @author 朴睦
 * @description 公共接口
 */
@RestController
@Slf4j
@Api(tags = "公共接口")
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private CommonService commonService;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        String upload = null;
        try {
            upload = commonService.upload(file);
        } catch (IOException e) {
            throw new BaseException("上传失败");
        }

        return Result.success(upload);
    }
}
