package com.ankang.acme.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Enumeration;

public class JmsQueueConsumer {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");

        Connection connection=null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("myQueue");
            MessageConsumer consumer = session.createConsumer(destination);

            TextMessage receive = (TextMessage)consumer.receive();
            //接收应用程序设置自定义属性
            System.out.println(receive.getStringProperty("key"));
            Enumeration jmsxPropertyNames = connection.getMetaData().getJMSXPropertyNames();
            while (jmsxPropertyNames.hasMoreElements()){
                String name = (String)jmsxPropertyNames.nextElement();
                System.out.println(name+":"+receive.getStringProperty(name));
            }
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
