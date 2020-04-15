package rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import javax.servlet.Servlet;
import java.util.List;

public class Producer {
    public static void main(String[] args) throws Exception{

        DefaultMQProducer defaultMQProducer=new DefaultMQProducer("ProducerGroup1");
        defaultMQProducer.setNamesrvAddr("192.168.1.109:9876;192.168.1.108:9876");
        defaultMQProducer.start();
        for(int i=0;i<10;i++){
            Message message=new Message("example1",("hello world"+i).getBytes());
            SendResult sendResult=defaultMQProducer.send(message);
            System.out.println(sendResult);
        }

        defaultMQProducer.shutdown();

    }
}
