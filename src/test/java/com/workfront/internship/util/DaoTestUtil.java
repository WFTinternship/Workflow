package com.workfront.internship.util;

import com.workfront.internship.dataModel.User;

import java.util.UUID;

/**
 * Created by Karen on 5/29/2017.
 */
public class DaoTestUtil {

    public static User getRandomUser(){
        User user = new User();
        user.setFirstName("name" + uuid()).setLastName("surname" + uuid())
                .setEmail("name@gmail.com")
                .setPassword("password" + uuid()).setRating(12);
        return user;
    }

    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
