package com.ligeit.ec.JobService.Jobs;

import com.alibaba.fastjson.JSONObject;
import com.ligeit.ec.JobService.RabbitConfig;
import com.ligeit.ec.JobService.service.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 定时异步消息job 示例
 *
 * @author ywx
 * @date 2019/6/18
 */
@Component(Job.START_ORDER_TIMING_JOB)
public class TimingJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(TimingJob.class);


    //TODO 注入 service...



    @Override
    public void execute(JSONObject params) {
        //TODO 执行业务代码...
        log.info("开始执行定时异步消息 job 业务代码...");

    }
}
