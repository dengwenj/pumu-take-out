package vip.dengwj.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pumu.jwt")
@Data
public class JWTProperties {
    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private Long adminTtl;
    private String adminTokenName;
}
