package vip.dengwj.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyTask {
    // 定时任务，每隔 5 秒触发一次
    //@Scheduled(cron = "0/5 * * * * ?")
    //public void executionTask() {
    //    log.info("定时任务开始执行：{}", System.currentTimeMillis() );
    //}
}
