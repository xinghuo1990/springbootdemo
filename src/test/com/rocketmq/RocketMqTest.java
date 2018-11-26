package test.com.rocketmq;

import com.SpringbootApplication;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)
public class RocketMqTest {

    @Autowired
    private DefaultMQProducer producer;

//    @Autowired
//    private DefaultMQPushConsumer consumer;

    @Test
    public void testProducerRocketMQ() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = null;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 10; i++) {
            message= new Message("0TNZQ5T6X9YTWPI","tag",("rocketmq第"+(i+1)+"测试").getBytes());
            SendResult sendResult = producer.send(message);
            System.out.println("第"+(i+1)+"次发送响应：MsgId:" + sendResult.getMsgId() + "，发送状态:" + sendResult.getSendStatus());
        }
        stopWatch.stop();
        System.out.println("----------------发送10条消息耗时：" + stopWatch.getTotalTimeMillis());
    }


   /* @Test
    public void testConsumerRocketMq() throws MQClientException {
        //订阅PushTopic下Tag为push的消息
        consumer.subscribe("0TNZQ5T6X9YTWPI","tag");
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        //如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for(int a = 0 ;a<msgs.size(); a++){
                    MessageExt messageExt = msgs.get(a);
                    System.out.println("消费者消费第"+a+"数据:"+new String(messageExt.getBody()));
                }
//                for (MessageExt msg : msgs) {
//                    System.out.println("消费者消费第"+i+"数据:"+new String(msg.getBody()));
//                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }*/
}
