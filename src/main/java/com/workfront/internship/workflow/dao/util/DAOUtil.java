package com.workfront.internship.workflow.dao.util;

import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vahag on 6/15/2017
 */
public class DAOUtil {

    private static final Logger LOGGER = Logger.getLogger(DAOUtil.class);

    /**
     * Sets comments values from result set
     */
    public static Comment commentFromResultSet(ResultSet rs) {
        return commentFromResultSet(rs, null);
    }

    public static Comment commentFromResultSet(ResultSet rs, String tableAlias) {
        Comment comment = new Comment();

        try {
            comment.setId(rs.getLong(CommentDAO.id));

            User user = UserDAOImpl.fromResultSet(rs);
            user.setId(rs.getLong(UserDAO.id));
            comment.setUser(user);

            Post post = DAOUtil.postFromResultSet(rs);
            post.setId(rs.getLong(PostDAO.id));
            comment.setPost(post);

            comment.setContent(rs.getString(getColumnName(CommentDAO.content, tableAlias)));
            comment.setCommentTime(rs.getTimestamp(CommentDAO.dateTime));
        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred");
        }
        return comment;
    }

    private static String getColumnName(String column, String tableName) {
        return tableName == null ? column : tableName + "." + column;
    }


    /**
     * Sets users fields values from result set
     */
    public static User userFromResultSet(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getLong(UserDAO.id));
            user.setFirstName(rs.getString(UserDAO.firstName));
            user.setLastName(rs.getString(UserDAO.lastName));
            user.setPassword(rs.getString(UserDAO.password));
            user.setEmail(rs.getString(UserDAO.email));
            user.setAvatarURL(rs.getString(UserDAO.avatarURl));
            user.setRating(rs.getInt(UserDAO.rating));
        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return user;
    }

    /**
     * Sets posts fields values from result set
     */
    public static Post postFromResultSet(ResultSet rs) {
        Post post = new Post();
        try {
            post.setId(rs.getLong(PostDAO.id));

            AppArea appArea = AppArea.getById(rs.getLong(PostDAO.appAreaId));
            post.setAppArea(appArea);

            User user = UserDAOImpl.fromResultSet(rs);
            user.setId(rs.getLong(PostDAO.userId));
            post.setUser(user);

            post.setPostTime(rs.getTimestamp(PostDAO.postTime));
            post.setTitle(rs.getString(PostDAO.postTitle));
            post.setContent(rs.getString(PostDAO.postContent));

            try {
                Post parentPost = new Post();
                parentPost.setId(rs.getLong(PostDAO.parentId));

                if (parentPost.getId() == 0){
                    post.setPost(null);
                    return post;
                }

                User parentUser = new User();
                parentUser.setId(rs.getLong(PostDAO.parentUserId));
                parentUser.setFirstName(rs.getString(PostDAO.parentUserFirstName));
                parentUser.setLastName(rs.getString(PostDAO.parentUserLastName));
                parentUser.setEmail(rs.getString(PostDAO.parentUserEmail));
                parentUser.setPassword(rs.getString(PostDAO.parentUserPasscode));
                parentUser.setAvatarURL(rs.getString(PostDAO.parentUserAvatar));
                parentUser.setRating(rs.getInt(PostDAO.parentUserRating));
                parentPost.setUser(parentUser);

                AppArea parentAppArea = AppArea.getById(rs.getLong(PostDAO.parentAppAreaId));
                parentPost.setAppArea(parentAppArea);

                parentPost.setPostTime(rs.getTimestamp(PostDAO.parentTime));
                parentPost.setContent(rs.getString(PostDAO.parentContent));
                parentPost.setTitle(rs.getString(PostDAO.parentTitle));

                post.setPost(parentPost);
            } catch (SQLException e) {
                post.setPost(null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }


    /**
     * Sets answers fields values from result set
     */
    public static Post answerFromResultSet(ResultSet rs) {
        Post answer = new Post();
        try {
            answer.setId(rs.getLong(PostDAO.id));

            AppArea appArea = AppArea.getById(rs.getLong(PostDAO.appAreaId));
            answer.setAppArea(appArea);

            User user = UserDAOImpl.fromResultSet(rs);
            user.setId(rs.getLong(PostDAO.userId));
            answer.setUser(user);

            answer.setPostTime(rs.getTimestamp(PostDAO.answerTime));
            answer.setTitle(rs.getString(PostDAO.answerTitle));
            answer.setContent(rs.getString(PostDAO.answerContent));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
