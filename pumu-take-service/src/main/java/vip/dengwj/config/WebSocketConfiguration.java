package vip.dengwj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类，用于注册WebSocket的Bean
 */
@Configuration
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        // ServerEndpointExporter是Spring Boot为WebSocket提供的一个bean，用于自动注册@ServerEndpoint注解声明的WebSocket端点
        return new ServerEndpointExporter();
    }

}
