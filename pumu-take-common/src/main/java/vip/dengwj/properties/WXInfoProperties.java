package vip.dengwj.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("wechat")
@Data
public class WXInfoProperties {
    private String appId;
    private String appSecret;
}
