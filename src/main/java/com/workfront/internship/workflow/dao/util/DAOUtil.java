package com.workfront.internship.workflow.dao.util;

import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vahag on 6/15/2017
 */
public class DAOUtil {

    private static final Logger LOGGER = Logger.getLogger(DAOUtil.class);


    // USER FIELDS
    public static final String user_id = "id";
    public static final String firstName = "first_name";
    public static final String lastName = "last_name";
    public static final String email = "email";
    public static final String password = "passcode";
    public static final String avatarURl = "avatar_url";
    public static final String rating = "rating";

    // COMMENT FIELDS
    public static final String comment_id = "id";
    public static final String comment_user_id = "user_id";
    public static final String comment_post_id = "post_id";
    public static final String comment_content = "content";
    public static final String comment_time = "comment_time";

    // POST FIELDS
    public static final String post_id = "id";
    /**
     * Sets comemnts values from result set
     */
    public static Comment commentFromResultSet(ResultSet rs){

        User user = new User();
        Post post = new Post();
        Comment comment = new Comment();
        try{
            user = userFromResultSet(rs);
            user.setId(rs.getLong(user_id));

            post = postFromResultSet(rs);
            post.setId(rs.getLong(post_id));

            comment.setId(rs.getLong(comment_id));
            comment.setUser(user);
            comment.setPost(post);
            comment.setContent(rs.getString(comment_content));
            comment.setCommentTime(rs.getTimestamp(comment_time));

        }catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return comment;
    }
    /**
     * Sets users fields values from result set
     */
    public static User userFromResultSet(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getLong(user_id));
            user.setFirstName(rs.getString(firstName));
            user.setLastName(rs.getString(lastName));
            user.setEmail(rs.getString(email));
            user.setPassword(rs.getString(password));
            user.setAvatarURL(rs.getString(avatarURl));
            user.setRating(rs.getInt(rating));

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Sets posts fields values from result set
     */
    public static Post postFromResultSet(ResultSet rs){
        Post post = new Post();
        return post;
    }
}
