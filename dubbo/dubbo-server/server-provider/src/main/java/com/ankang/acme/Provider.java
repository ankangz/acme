package com.ankang.acme;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/dubbo-server.xml");
        context.start();
        System.in.read();
        //ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();
        //ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        //ExtensionLoader.getExtensionLoader(RegistryFactory.class).getExtension("zookeeper");
        //ExtensionLoader.getExtensionLoader(ZookeeperTransporter.class).getAdaptiveExtension();
        //ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();
    }

}
