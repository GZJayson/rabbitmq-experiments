package com.ligeit.ec.JobService;

import com.alibaba.fastjson.JSONObject;
import com.ligeit.ec.JobService.service.Job;
import com.ligeit.ec.JobService.service.JobBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ywx
 * @date 2019/5/29
 */
@RestController
@RequestMapping("/rabbit")
public class TestController {

    @GetMapping("/send/{time}")
    public String delayedTest(@PathVariable int time) {
        time = time>0?time:10;
        //发送延时消息
        JSONObject params3 = new JSONObject();
        params3.put("productId","1");
        JobBuilder.create(Job.START_PRODUCT_DELAYED_JOB).delay(time).send(params3);
        System.out.println("延时消息已发送(product)=======>>>");


        //发送异步消息
        JSONObject params1 = new JSONObject();
        params1.put("orderId","1");
        JobBuilder.create(Job.START_ORDER_TIMING_JOB).scheduled().send(params1);
        System.out.println("异步消息已发送(order)=======>>>");


        //发送定时异步消息
        JSONObject params2 = new JSONObject();
        params2.put("userId","1");
        JobBuilder.create(Job.START_USER_ANSYN_JOB).scheduled().send(params2);
        System.out.println("定时异步消息已发送(user)=======>>>");


        return "消息已发送: "+new Date();
    }
}
