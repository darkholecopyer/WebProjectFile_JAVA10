package com.osyunge.MQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class TopicConsumer {
    @Test
    public void receive() throws Exception{
        //1创建一个连接工厂（ActiveMQ的链接工厂）
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.122.129:61616");
        //2获取连接
        Connection connection = connectionFactory.createConnection();
        //3开启连接
        connection.start();
        //4根据连接对象创建session
        //第一个参数：表示是否使用分布式事务JTA
        //第二个参数：表示的是应答模式，
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5根据session创建destination
        Topic topic = session.createTopic("topic-test");
        //6创建消费者
        MessageConsumer consumer = session.createConsumer(topic);
        //7接收消息
        while (true){
            //接收消息（参数的值表示的是超过一定时间，以毫秒为单位，断开连接）
            Message message = consumer.receive(100000);
            //如果message为空，没有接收到消息就跳出
            if (message==null){
                break;
            }
            if (message instanceof TextMessage){
                TextMessage message1 = ((TextMessage) message);
                System.out.println(">>>>获取的消息内容："+((TextMessage) message).getText());
            }
        }

        //9关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
