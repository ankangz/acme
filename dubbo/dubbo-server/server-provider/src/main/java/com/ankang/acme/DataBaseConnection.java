package com.ankang.acme;

import java.util.ServiceLoader;

public class DataBaseConnection {

    public static void main(String[] args) {
        ServiceLoader<DataBaseDriver> serviceLoader = ServiceLoader.load(DataBaseDriver.class);
        for (DataBaseDriver dataBaseDriver : serviceLoader){
            System.out.println(dataBaseDriver.connection("all"));
        }
    }
}
