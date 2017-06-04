package com.workfront.internship.util;

import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dataModel.AppArea;
import com.workfront.internship.workflow.dataModel.Post;
import com.workfront.internship.workflow.dataModel.User;
import com.workfront.internship.dao.UserDAO;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.Comment;
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
    public static Comment getRandomComment(){
        Comment comment = new Comment();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
           date = dateFormat.parse("2017-05-30 17:00:07");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        comment.setUser(getRandomUser())
                .setPost(getRandomPost())
                .setContent("content")
                .setCommentTime(new Timestamp(date.getTime()));
        return comment;
    }
    public static Comment getRandomComment(User user, Post post) {
        Comment comment = new Comment();
        Date date = new Date();

        comment.setUser(user)
                .setPost(post)
                .setCommentTime(new Timestamp(date.getTime()))
                .setContent("content_" + System.currentTimeMillis());
        return comment;

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
                .setPost(null)
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

    public static Post getRandomAnswer(Post post){
        Post answer = new Post();
        User user = getRandomUser();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse("2017-05-30 13:50:58");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        answer.setPost(post)
                .setUser(user)
                .setTitle(post.getTitle())
                .setPostTime(new Timestamp(parsedDate.getTime()))
                .setAppArea(post.getAppArea())
                .setContent("answer's content");
        return answer;
    }

    public static AppArea getRandomAppArea(){
        return AppArea.values()[0];
    }

    public static String uuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}