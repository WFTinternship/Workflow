package com.workfront.internship.dao.impl;

import com.workfront.internship.dao.CommentDAO;
import com.workfront.internship.dataModel.Comment;
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
            Connection connection=DBHelper.getConnection();
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
    public List<Comment> getById(long postId) {
        List<Comment> comments=new ArrayList<>();
        String query="SELECT * FROM comment WHERE postId=?";
        try{

            Connection connection=DBHelper.getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, postId);
            ResultSet rs = stmt.executeQuery(query );
            while (rs.next()) {
                Comment comment = new Comment();
                comments.add(comment);
            }
            stmt.execute();

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return comments;
    }
}


