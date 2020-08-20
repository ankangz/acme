package com.ankang.acme.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsQueueProducer {
    public static void main(String[] args) {
        //设置异步发送1
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://127.0.0.1:61616?jms.useAsyncSend=true&jms.producerWindowSize=1024");

        Connection connection=null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
             //设置异步发送2 
            //((ActiveMQConnectionFactory)connectionFactory).setUseAsyncSend(true);
            //设置异步发送3
            /*((ActiveMQConnection)connection).setUseAsyncSend(true);*/
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("myQueue");
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage hello_myQueue = session.createTextMessage("Hello myQueue");
            TextMessage hello_myQueue2 = session.createTextMessage("Hello myQueues");
            //设置属性
            hello_myQueue.setStringProperty("key","value");
            

            producer.send(hello_myQueue);
            producer.send(hello_myQueue2);
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
