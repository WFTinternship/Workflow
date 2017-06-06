package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.dataModel.Comment;
import com.workfront.internship.workflow.dataModel.Post;
import com.workfront.internship.workflow.dataModel.User;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by angel on 27.05.2017.
 */
public class CommentDAOImpl implements CommentDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

        private static final String id = "id";
        private static final String userId = "user_id";
        private static final String postId = "post_id";
        private static final String content = "content";
        private static final String dateTime = "comment_time";

        /**
     * @see CommentDAO#add
     * @param comment
     * '@return'
     */
    @Override
    public long add(Comment comment) {
        long id = 0;
        String query = "INSERT INTO comment(user_id,post_id,content,comment_time)"+
                "VALUE(?,?,?,?)";
        try {
            Connection connection = DBHelper.getConnection(DBHelper.POOLED_CONNECTION);
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, comment.getUser().getId());
            stmt.setLong(2, comment.getPost().getId());
            stmt.setString(3, comment.getContent());
            stmt.setTimestamp(4, comment.getCommentTime());
            stmt.execute();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            comment.setId(id) ;
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException();
        }

        return comment.getId();
    }

    /**
     * @see CommentDAO#update(long, String)
     * '@param' id
     * @param newContent
     * '@return'
     */
    @Override
    public boolean update(long id , String newContent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String query = "UPDATE comment SET content = ?," +
                       " comment_time = ?" +
                       " WHERE comment.id = ?";

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DBHelper.getConnection(DBHelper.POOLED_CONNECTION);

            stmt = connection.prepareStatement(query);
            stmt.setString(1 , newContent);
            stmt.setString(2, dateFormat.format(date) );
            stmt.setLong(3, id);

            int rows = stmt.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            LOG.error("SQL exception");
            return false;
        } finally {
            closeResources(connection, stmt);
        }
    }

    /**
     * @see CommentDAO#delete(long)
     * @param id
     * '@return'
     */
    @Override
    public int delete(long id) {
        int n ;
        String query = "DELETE FROM comment WHERE id=?";
        try {
            Connection connection = DBHelper.getConnection(DBHelper.POOLED_CONNECTION);
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1,id);
            n = stmt.executeUpdate();
        }catch (SQLException e){
            LOG.error("SQL exception");
            return 0;
        }
        return n;
    }

    /**
     * @see CommentDAO#getById(long)
     * @param id
     * '@return'
     */
    @Override
    public Comment getById(long id) {
        Comment comment = null;
        String query = "SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, rating, comment.post_id, post_time, title, " +
                " post.content, comment_time, comment.content FROM comment " +
                " INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id WHERE comment.id = ?";

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = DBHelper.getConnection(DBHelper.POOLED_CONNECTION);
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            while (rs.next()) {
                comment = fromResultSet(rs, "comment");
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
        } finally {
            close(rs);
            closeResources(connection, stmt);
        }

        return comment;
    }

    /**
     *@see CommentDAO#getAll()
     * '@return'
     */
    @Override
    public List<Comment> getAll() {
        Comment comment ;
        List<Comment> comments = new ArrayList<>();
        String query = " SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, rating, comment.post_id, post_time, title, " +
                " post.content, comment_time, comment.content FROM comment " +
                " INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id ";
        try {

            Connection connection = DBHelper.getConnection(DBHelper.POOLED_CONNECTION);
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query );
            while (rs.next()) {
                comment = fromResultSet(rs);
                comments.add(comment);
            }

        } catch (SQLException e){
            LOG.error("SQL exception");
            return null;
        }

        return comments;
    }

    private static Comment fromResultSet(ResultSet rs) {

        return fromResultSet(rs, null);

    }

    private static Comment fromResultSet(ResultSet rs, String tableAlias) {
        Comment comment = new Comment();

        try {
            comment.setId(rs.getLong(CommentDAOImpl.id));

            User user = new User();
            user = UserDAOImpl.fromResultSet(user,rs);
            user.setId(rs.getLong(CommentDAOImpl.userId));
            comment.setUser(user);

            Post post = new Post();
            post = PostDAOImpl.fromResultSet(post,rs);
            post.setId(rs.getLong(CommentDAOImpl.postId));
            comment.setPost(post);

            comment.setContent(rs.getString(getColumnName(CommentDAOImpl.content, tableAlias)));
            comment.setCommentTime(rs.getTimestamp(CommentDAOImpl.dateTime));
        } catch (SQLException e) {
            LOG.error("SQL exception");
        }
        return comment;
    }

    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeResources(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
        }
    }


    private static String getColumnName(String column, String tableName) {
        return tableName == null ? column : tableName + "." + column;
    }

}


