package com.workfront.internship.util;

import com.workfront.internship.dao.UserDAO;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;

import javax.jws.soap.SOAPBinding;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static Post getRandomPost()  {
        Post post = new Post();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse("2017-05-30 13:50:58");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        post.setAppArea(getRandomAppArea())
                .setPostTime(new Timestamp(parsedDate.getTime()))
                .setTitle("title")
                .setContent("content");

        return post;
    }

    public static Post getRandomPost(User user, AppArea appArea)  {
        Post post = getRandomPost();
        post.setUser(user);
        post.setAppArea(appArea);
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
