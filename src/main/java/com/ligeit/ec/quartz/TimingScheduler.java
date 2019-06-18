package com.ligeit.ec.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *  原定时器任务示例
 *  改造前 ===>> 执行业务代码
 *  改造后 ===>> 发送消息到 mq
 *
 * @author ywx
 * @date 2019/6/18
 */
@Component
public class TimingScheduler {

    @Scheduled(cron="0 10 0 * * ?")
    public void checkOrderSuccessJob() {
        //TODO 改造前 执行业务
        //TODO 改造后 发送消息
    }
}
