package com.osyunge.MQ;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class QueueConsumer {
    @Test
    public void receive() throws Exception {
        //创建一个连接工厂，指定连接activemq的服务
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.122.129:61616");
        //获取连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //根据连接对象创建session
        //第一个参数：表示是否使用分布式事务JTA
        //第二个参数：如果第一个参数为false，第二个参数才有意义：
        //表示使用的应答模式：自动应答，手动应答，这里选择自动应答
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //根据session创建destination
        Queue queue = session.createQueue("queue-test");
        //创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //接收消息
        System.out.println("开始-------------");
        //设置监听器
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage){
                    TextMessage message1 = ((TextMessage) message);
                    try {
                        System.out.println(">>>获取信息内容："+((TextMessage) message).getText());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println("end------------");
        Thread.sleep(10000);
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
