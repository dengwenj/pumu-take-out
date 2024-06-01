package vip.dengwj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vip.dengwj.service.CommonService;
import vip.dengwj.utils.AliyunOSSUtils;

import java.io.IOException;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @Override
    public String upload(MultipartFile file) throws IOException {
        return aliyunOSSUtils.upload(file);
    }
}
