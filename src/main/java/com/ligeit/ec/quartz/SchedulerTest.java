package com.ligeit.ec.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 原 quartz 实现的延时任务示例
 * 改造前 ===>>>调用本方法,手工创建定时器,设置 n 分钟后执行业务代码(调度器启动状态下)
 * 改造后 ===>>>调用本方法,发送延时消息到 mq
 *
 * @author ywx
 * @date 2019/5/28
 */
@Component
public class SchedulerTest {


    private static Logger _log = LoggerFactory.getLogger(Scheduler.class);

    public static void main(String[] args) {
        try {

            //1.创建Scheduler的工厂
            SchedulerFactory sf = new StdSchedulerFactory();
            //2.从工厂中获取调度器实例
            Scheduler scheduler = sf.getScheduler();

            //3.创建JobDetail
            JobDetail jb = JobBuilder.newJob(Show.class) // Show 为一个job,是要执行的一个任务。
                    .withDescription("这是我的测试定时任务。") //job的描述
                    .withIdentity("jy2Job", "jy2Group") //job 的name和group
                    .build();

            //任务运行的时间，SimpleSchedle类型触发器有效
            long time = System.currentTimeMillis() + 60 * 1000L; // 3秒后启动任务
            Date statTime = new Date(time);

            //4.创建Trigger
            //使用SimpleScheduleBuilder或者CronScheduleBuilder
            Trigger t = TriggerBuilder.newTrigger()
                    .withDescription("")
                    .withIdentity("jyTrigger", "jyTriggerGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                    .startAt(statTime)  //默认当前时间启动 ,也可以写为：.startNow();
//                    .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) //两秒执行一次
                    .build();

            //5.注册任务和定时器
            scheduler.scheduleJob(jb, t);

            //6.启动 调度器
            scheduler.start();
            _log.info("启动时间 ： " + new Date());

        } catch (Exception e) {
            _log.info("定时任务出现异常 ： " + e);
        }
    }
}