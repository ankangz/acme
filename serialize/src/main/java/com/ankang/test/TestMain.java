package com.ankang.test;

import com.ankang.serialize.JavaSerialize;
import com.ankang.serialize.User;

public class TestMain {

    public static void main(String[] args) {
        User user = new User();
        JavaSerialize javaSerialize = new JavaSerialize();
        byte[] data = javaSerialize.serialize(user);

        System.out.println(data.length);

        User user1 = javaSerialize.deSerialize(data, User.class);
        System.out.println(user1 == user);


    }
}
