package com.workfront.internship.util;

import com.workfront.internship.dao.UserDAO;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;

import javax.jws.soap.SOAPBinding;
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

    public static Post getRandomPost(){
        Post post = new Post();
        UserDAO userDAO = new UserDAOImpl();
        post.setUser(userDAO.getById(11))
                .setAppArea(getRandomAppArea())
                .setPostTime("11/11/11 12:00:00")
                .setTitle("title")
                .setContent("content");

        return post;
    }

    public static AppArea getRandomAppArea(){
        return AppArea.values()[0];
    }

    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
