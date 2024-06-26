package vip.dengwj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // 开启注解方式的事务管理
@EnableCaching // 开启缓存注解功能
@EnableScheduling // 开启任务调度
public class PumuTakeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PumuTakeServiceApplication.class, args);
    }
}
