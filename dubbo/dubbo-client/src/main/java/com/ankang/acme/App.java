package com.ankang.acme;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("dubbo-client.xml");
        applicationContext.start();
       IHelloService helloService = (IHelloService) applicationContext.getBean("HelloService");
        System.out.println(helloService.sayHello("ankang"));
    }
}
