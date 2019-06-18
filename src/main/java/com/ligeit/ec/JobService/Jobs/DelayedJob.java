package com.ligeit.ec.JobService.Jobs;

import com.alibaba.fastjson.JSONObject;
import com.ligeit.ec.JobService.RabbitConfig;
import com.ligeit.ec.JobService.service.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 延时消息示例
 *
 * @author ywx
 * @date 2019/6/18
 */
@Component(Job.START_PRODUCT_DELAYED_JOB)
public class DelayedJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(DelayedJob.class);


    //TODO 注入 service...



    @Override
    public void execute(JSONObject params) {
        //TODO 执行业务代码...
        log.info("开始执行延时消息 job 业务代码...");
    }
}
