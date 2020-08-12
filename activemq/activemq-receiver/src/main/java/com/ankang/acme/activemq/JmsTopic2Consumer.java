package com.ankang.acme.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopic2Consumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        Connection connection=null;
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("ankang");
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Topic destination = session.createTopic("myTopic");
            MessageConsumer consumer = session.createDurableSubscriber(destination,"ankang");
            TextMessage receive = (TextMessage)consumer.receive();
            //接收应用程序设置自定义属性
            System.out.println(receive.getText());
            
            session.commit();
            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(null != connection){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
