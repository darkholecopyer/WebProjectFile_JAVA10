package com.osyunge.MQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

public class TopicProducer {
    @Test
    public void send() throws Exception{
        //1创建一个连接工程（Activemq的连接工厂）参数：指定连接的activemq的服务
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.122.129:61616");
        //2获取连接
        Connection connection = connectionFactory.createConnection();
        //3开启连接
        connection.start();
        //4根据连接对象创建session
        //第一个参数：表示是否使用分布式事务JTA
        //第二个参数：如果第一个参数为false，第二个参数才有意义，表示使用的应答模式，自动应答，手动应答，这里
        //选择自动应答
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5根据session创建Destination（目的地，这里使用topic）
        Topic topic = session.createTopic("topic-test");
        //6创建生产者
        MessageProducer producer = session.createProducer(topic);
        //7构建消息对象
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("发送消息");
        //8发送消息
        producer.send(textMessage);
        //9关闭资源
        producer.close();
        session.close();
        connection.close();

    }
}
