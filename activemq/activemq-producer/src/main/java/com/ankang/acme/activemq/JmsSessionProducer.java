package com.ankang.acme.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsSessionProducer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        Connection connection=null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.FALSE, Session.DUPS_OK_ACKNOWLEDGE);
            Destination destination = session.createQueue("myQueue");
            MessageProducer producer = session.createProducer(destination);
            
            TextMessage hello_myQueue = session.createTextMessage("Hello myQueue");
            //设置属性
            hello_myQueue.setStringProperty("key","value");
            

            producer.send(hello_myQueue);
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
