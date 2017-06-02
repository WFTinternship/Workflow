package com.workfront.internship.dao.impl;

import com.workfront.internship.dao.CommentDAO;
import com.workfront.internship.dataModel.Comment;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.dbConstants.DataBaseConstants;
import com.workfront.internship.util.DBHelper;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by angel on 27.05.2017.
 */
public class CommentDAOImpl implements CommentDAO {
    /*public static class Comment{
        public static String id = "id";
        public static String userId = "user_id";
        public static String postId = "post_id";
        public static String content = "content";
        public static String dateTime = "comment_time";
    }*/

    @Override
    public long add(Comment comment) {
        long id = 0;
        String query="INSERT INTO comment(user_id,post_id,content,comment_time)"+
                "VALUE(?,?,?,?)";
        try{
            Connection connection= DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
          //  stmt.setLong(1, comment.getId());
            stmt.setLong(1, comment.getUser().getId());
            stmt.setLong(2, comment.getPost().getId());
            stmt.setString(3, comment.getContent());
            stmt.setTimestamp(4, comment.getCommentTime());
            stmt.execute();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            comment.setId(id);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return comment.getId();
    }

    @Override
    public boolean update(long id, String content) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String query="UPDATE comment SET content = ?, comment_time = ?" +
                " WHERE comment.id = ?";

        Connection connection = null;
        PreparedStatement stmt = null;
        try{
            connection = DBHelper.getConnection();

            stmt = connection.prepareStatement(query);
            stmt.setString(1, content);
            stmt.setString(2, dateFormat.format(date));
            stmt.setLong(3, id);

            int rows = stmt.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(connection, stmt);
        }
    }

    @Override
    public boolean delete(long id) {
        String query="DELETE FROM comment WHERE id=?";
        try{
            Connection connection = DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1,id);
            stmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Comment getById(long id) {
        Comment comment = null;
        String query="SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, rating, comment.post_id, post_time, title, " +
                " post.content, comment_time, comment.content FROM comment " +
                " INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id WHERE comment.id = ?";

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = DBHelper.getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            while (rs.next()) {
                comment = fromResultSet(rs, "comment");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            close(rs);
            closeResources(connection, stmt);
        }

        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Comment comment;
        List<Comment> comments=new ArrayList<>();
        String query="SELECT * FROM comment";
        try{

            Connection connection=DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query );
            while (rs.next()) {
                comment = fromResultSet(rs);
                comments.add(comment);
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return comments;
    }

    public static Comment fromResultSet(ResultSet rs){
        return fromResultSet(rs, null);
    }

    public static Comment fromResultSet(ResultSet rs, String tableAlias) {
        Comment comment = new Comment();
        try {
            comment.setId(rs.getLong(DataBaseConstants.Comment.id));

            User user = new User();
            user = UserDAOImpl.fromResultSet(user,rs);
            user.setId(rs.getLong(DataBaseConstants.Comment.userId));
            comment.setUser(user);

            Post post = new Post();
            post = PostDAOImpl.fromResultSet(post,rs);
            post.setId(rs.getLong(DataBaseConstants.Comment.postId));
            comment.setPost(post);

            comment.setContent(rs.getString(getColumnName(DataBaseConstants.Comment.content, tableAlias)));
            comment.setCommentTime(rs.getTimestamp(DataBaseConstants.Comment.dateTime));
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    private static String getColumnName(String column) {
        return getColumnName(column, null);
    }

    private static String getColumnName(String column, String tableName) {
        return tableName == null ? column : tableName + "." + column;
    }

}


