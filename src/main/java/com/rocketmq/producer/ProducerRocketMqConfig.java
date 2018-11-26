package com.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Configuration
public class ProducerRocketMqConfig {

    private static  final Logger logger = LoggerFactory.getLogger(ProducerRocketMqConfig.class);

    @Value("${spring.rocketmq.nameServerAddr}")
    private String nameServerAddr;

    @Value("${spring.rocketmq.producer-group-name}")
    private String producerGroup;

    @Bean
    public DefaultMQProducer producer(){
        logger.info("rocketmq生产者启动开始");
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServerAddr);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(3);
        try {
            producer.start();
//            Message message = new Message("0TNZQ5T6X9YTWPI","tag","rocketmq测试".getBytes());
//            StopWatch stopWatch = new StopWatch();
//            stopWatch.start();
//            for (int i = 0; i < 10; i++) {
//                SendResult sendResult = producer.send(message);
//                System.out.println("发送响应：MsgId:" + sendResult.getMsgId() + "，发送状态:" + sendResult.getSendStatus());
//            }
//            stopWatch.stop();
//            System.out.println("----------------发送10条消息耗时：" + stopWatch.getTotalTimeMillis());
        } catch (MQClientException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (RemotingException e) {
//            e.printStackTrace();
//        } catch (MQBrokerException e) {
//            e.printStackTrace();
        }
        logger.info("rocketmq生产者启动结束");
        return producer;
    }
}

