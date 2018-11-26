package com.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
//@ConfigurationProperties(value = "")
public class ConsumerRocketMqConfig {

    private static  final Logger logger = LoggerFactory.getLogger(ConsumerRocketMqConfig.class);


    @Value("${spring.rocketmq.nameServerAddr}")
    private String nameServerAddr;

    @Value("${spring.rocketmq.consumer-group-name}")
    private String consumerGroup;

    @PostConstruct
    public void defaultMQPushConsumer(){
        logger.info("rocketmq消费端初始化开始");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.setVipChannelEnabled(false);
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("0TNZQ5T6X9YTWPI","tag");
            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        System.out.println("消费者消费数据:"+new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        logger.info("rocketmq消费端初始化结束");
    }
}
