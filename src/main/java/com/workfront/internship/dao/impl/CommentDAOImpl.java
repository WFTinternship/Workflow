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

    @Override
    public long add(Comment comment) {
        long id = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String query="INSERT INTO COMMENT(id,userId,postId,content,dateTime)"+
                "VALUES(?,?,?,?,?)";
        try{
            Connection connection= DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, comment.getId());
            stmt.setObject(2, comment.getUser());
            stmt.setObject(3, comment.getPost());
            stmt.setString(4, comment.getContent());
            stmt.setString(5, dateFormat.format(date));

            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            comment.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comment.getId();
    }

    @Override
    public boolean update(long id, String content) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String query="UPDATE COMMENT SET content=?,dateTime=? WHERE id=?";
        try{
            Connection connection= DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, content);
            stmt.setString(2, dateFormat.format(date));
            stmt.setLong(3, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        String query="SELECT * FROM comment WHERE id = ?";
        try {
            Connection connection = DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                comment = new Comment();
                comment = fromResultSet(comment,rs);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return comment;
    }

    @Override
    public List<Comment> getAll() {
        Comment comment = null;
        List<Comment> comments=new ArrayList<>();
        String query="SELECT * FROM comment";
        try{

            Connection connection=DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query );
            while (rs.next()) {
                comment = new Comment();
                comments.add(fromResultSet(comment,rs));
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return comments;
    }
    public static Comment fromResultSet(Comment comment, ResultSet rs){
        try {
            comment.setId(rs.getLong(DataBaseConstants.Comment.id));
            User user = new User();
            UserDAOImpl.fromResultSet(user,rs);
            comment.setUser(user);
            Post post = new Post();
            PostDAOImpl.fromResultSet(post,rs);
            comment.setPost(post);
            comment.setContent(rs.getString(DataBaseConstants.Comment.content));
            comment.setCommentTime(rs.getTime(DataBaseConstants.Comment.dateTime));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return comment;
    }
}


