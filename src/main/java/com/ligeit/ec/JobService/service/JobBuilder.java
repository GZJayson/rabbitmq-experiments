package com.ligeit.ec.JobService.service;

import com.alibaba.fastjson.JSONObject;
import com.ligeit.ec.JobService.RabbitConfig;
import com.ligeit.ec.JobService.utils.SpringContextUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * job 构造类
 *
 * @author ywx
 * @date 2019/5/24
 */
@Data
@Component
@Scope("prototype")
public class JobBuilder{
    protected static final Logger log = LoggerFactory.getLogger(JobBuilder.class);


    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;

    private String name;
    private String exchange = RabbitConfig.DEV_IMMEDIATE_EXCHANGE;
    private String routing = RabbitConfig.ROUTINGKEY_ASYNC;
    private long delay = 0;
    private boolean locked = false; //使用了锁机制

    public static JobBuilder create(String name) {
        JobBuilder builder = SpringContextUtils.getBean(JobBuilder.class);
        builder.setName(name);
        return builder;
    }

    //构建异步任务消息对象
    public JobBuilder scheduled(){
        this.locked = true;
        this.routing = RabbitConfig.ROUTINGKEY_TIMING;
        return this;
    }

    //构建延迟任务消息对象
    public JobBuilder delay(long delay) {
        this.exchange = RabbitConfig.DEV_DELAY_EXCHANGE;
        this.routing = RabbitConfig.ROUTINGKEY_DELAYED;
        if (delay > 0) {
            this.delay = delay;
        }
        return this;
    }

    public void send(JSONObject params) {
        //检查是否使用了锁机制，使用先检查标识是否存在，1分钟内不重复执行,
        // 在分布式环境下使用 quartz 发送消息时这里需要加锁避免定时时间一到多台机器同时发送消息
//        if (locked) {
//            //锁存在直接返回
//            if("true".equals(redisTemplate.opsForValue().get(name))) {
//                log.info("Job({}) already running!",name);
//                return;
//            }
//            redisTemplate.opsForValue().set(name,"true", 60, TimeUnit.SECONDS);
//        }
        if (params == null) params = new JSONObject();
        params.put("job_name", name);
        rabbitTemplate.convertAndSend(exchange,routing, params, message -> {
            //延时任务需额外设置延时时间 x-delay 值单位为毫秒
            if (delay > 0) message.getMessageProperties().setHeader("x-delay", delay * 1000);
            return message;

        });
    }
}
