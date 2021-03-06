package com.ankang.acme.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsTopicProducer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        Connection connection=null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("myTopic");
            MessageProducer producer = session.createProducer(destination);
            
            TextMessage hello_myQueue = session.createTextMessage("Hello myQueue");
            //设置属性
            hello_myQueue.setStringProperty("key","value");
            

            producer.send(hello_myQueue);
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
