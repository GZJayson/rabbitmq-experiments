package com.ligeit.ec.JobService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * RabbitMQ配置
 *
 * @author ywx
 * @date 2019/5/29
 */
@Configuration
@ConditionalOnProperty(prefix="mq",name = "open", havingValue = "true")
public class RabbitConfig {


    private static final Logger log = LoggerFactory.getLogger(RabbitConfig.class);


    /**
     * 交换器
     */
    public static final  String DEV_IMMEDIATE_EXCHANGE = "dev.immediate.exchange";
    public static final  String DEV_DELAY_EXCHANGE = "dev.delayed.exchange";



    //异步任务队列
    public static final String ASYNC_QUEUES ="async.queues";
    //定时任务队列
    public static final String TIMING_QUEUES ="timing.queues";
    //延时任务队列
    public static final String DELAYED_QUEUES ="delayed.queues";



    //定时任务队列路由键
    public static final String ROUTINGKEY_TIMING ="routingkey.timing";
    //异步任务队列路由键
    public static final String ROUTINGKEY_ASYNC = "routingkey.async";
    //延迟任务队列路由键
    public static final String ROUTINGKEY_DELAYED = "routingkey.delayed";


    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(
                (correlationData, ack, cause) -> {
                    log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
                });
        rabbitTemplate.setReturnCallback(
                (message, replyCode, replyText, exchange, routingKey) -> {
                    if(!DEV_DELAY_EXCHANGE.equals(exchange)){
                        log.debug("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
                    }
                });
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DEV_IMMEDIATE_EXCHANGE);
    }

    //异步任务公共队列
    @Bean
    public Queue AsyncQueues() {
        return new Queue(ASYNC_QUEUES, true);
    }
    @Bean
    public Binding asyncBinding() {
        return BindingBuilder.bind(AsyncQueues()).to(delayExchange()).with(ROUTINGKEY_ASYNC);
    }

    //定时任务公共队列
    @Bean
    public Queue TimingQueues() {
        return new Queue(TIMING_QUEUES, true);
    }
    @Bean
    public Binding timingUserBegin() {
        return BindingBuilder.bind(TimingQueues()).to(delayExchange()).with(ROUTINGKEY_TIMING);
    }

    //rabbitmq 插件实现延时任务 start 自定义的交换机类型
    @Bean
    public CustomExchange delayedExchange() {
        Map<String,Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DEV_DELAY_EXCHANGE,"x-delayed-message",true,false,args);
    }

    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUES,true);
    }

    //绑定队列到自定义交换机
    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(delayedQueue()).to(delayedExchange()).with(ROUTINGKEY_DELAYED).noargs();
    }

    //rabbitmq 插件实现延时任务 end
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置手动 ack
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);

        factory.setConcurrentConsumers(10);  //设置线程数
        factory.setMaxConcurrentConsumers(10); //最大线程数
        configurer.configure(factory,connectionFactory);
        return factory;
    }
    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Bean
    public RabbitAdmin rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}