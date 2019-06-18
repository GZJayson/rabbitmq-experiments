package com.ligeit.ec.JobService.Consumer;

import com.alibaba.fastjson.JSONObject;
import com.ligeit.ec.JobService.RabbitConfig;
import com.ligeit.ec.JobService.service.Job;
import com.ligeit.ec.JobService.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * 消费者
 *
 * @author ywx
 * @date 2019/5/24
 */

@Service
public class JobConsumer {

    private static final Logger log = LoggerFactory.getLogger(JobConsumer.class);

//,autoStartup="${mq.open}"
        @RabbitListener(queues = RabbitConfig.ASYNC_QUEUES,autoStartup="${mq.open}")
        public void async(JSONObject params){
            run(params);
        }

        @RabbitListener(queues = RabbitConfig.TIMING_QUEUES,autoStartup="${mq.open}")
        public void timing(JSONObject params){
            run(params);
        }

        @RabbitListener(queues = RabbitConfig.DELAYED_QUEUES,autoStartup="${mq.open}")
        public void delay(JSONObject params){
            run(params);
        }

        private void run(JSONObject params){
            String name = params.getString("job_name");
            log.info("run job({}) and params({})",name, params);
            Job job = (Job) SpringContextUtils.getBean(name);
            //执行业务代码
            job.execute(params);
            log.info("run job({}) commit!", name);
        }


}

