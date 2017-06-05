package com.workfront.internship.workflow.service.util;

import com.workfront.internship.workflow.dataModel.AppArea;
import com.workfront.internship.workflow.dataModel.Comment;
import com.workfront.internship.workflow.dataModel.Post;
import com.workfront.internship.workflow.dataModel.User;

/**
 * Created by nane on 6/5/17.
 */
public class Validator {

    public static boolean isValidPost(Post post) {
        return post != null
                && isValidUser(post.getUser())
                && !isEmpty(post.getTitle())
                && !isEmpty(post.getContent())
                && post.getAppArea() != null;
    }

    public static boolean isValidUser(User user) {
            return user != null
                    && !isEmpty(user.getFirstName())
                    && !isEmpty(user.getLastName())
                    && !isEmpty(user.getEmail())
                    && !isEmpty(user.getPassword());
    }
    public static boolean isValidComment(Comment comment){
        return comment != null
                && isValidPost(comment.getPost())
                && isValidUser(comment.getUser())
                && !isEmpty(comment.getContent());
    }

    public static boolean isValidAppArea(AppArea appArea){
        return appArea != null
                && isEmpty(appArea.getName())
                && isEmpty(appArea.getDescription())
                && isEmpty(appArea.getTeamName());
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
