package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel on 27.05.2017
 */
@Repository
public class CommentDAOImpl extends AbstractDao implements CommentDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    public CommentDAOImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    /**
     * @see CommentDAO#add
     */
    @Override
    public long add(Comment comment) {
        long id = 0;
        String query = "INSERT INTO comment(user_id,post_id,content,comment_time) "+
                "VALUE(?,?,?,?)";
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet;
        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, comment.getUser().getId());
            stmt.setLong(2, comment.getPost().getId());
            stmt.setString(3, comment.getContent());
            stmt.setTimestamp(4, comment.getCommentTime());
            stmt.execute();
            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            comment.setId(id) ;
        } catch (SQLException e) {
            LOG.error("SQL exception occurred");
            throw new DAOException();
        } finally {
            closeResources(connection, stmt);
        }
        return comment.getId();
    }

    /**
     * @see CommentDAO#update(long, String)
     */
    @Override
    public boolean update(long id , String newContent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String query = "UPDATE comment SET content = ?, " +
                       " comment_time = ?" +
                       " WHERE comment.id = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setString(1 , newContent);
            stmt.setString(2, dateFormat.format(date) );
            stmt.setLong(3, id);

            int rows = stmt.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException(e);
        } finally {
            closeResources(connection, stmt);
        }
    }

    /**
     * @see CommentDAO#getByPostId(long)
     */
    @Override
    public List<Comment> getByPostId(long postId) {
        List<Comment> commentList = new ArrayList<>();
        String query = "SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, avatar_url, rating, comment.post_id, post_time, title, " +
                " post.content, apparea_id, comment_time, comment.content FROM comment " +
                " INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id WHERE comment.post_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            while (rs.next()){
                Comment comment = DAOUtil.commentFromResultSet(rs, "comment");
                commentList.add(comment);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return commentList;
    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Override
    public void delete(long id) {
        int numberOfRowsAffected = 0 ;
        String query = "DELETE FROM comment WHERE id=?";

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setLong(1,id);

            numberOfRowsAffected = stmt.executeUpdate();

        }catch (SQLException e){
            LOG.error("SQL exception occurred");
            throw new DAOException();

        } finally {
            closeResources(connection, stmt);
        }
        if(numberOfRowsAffected == 0){
            LOG.error("No rows affected");
        }
    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Override
    public Comment getById(long id) {
        Comment comment = null;
        String query = "SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, avatar_url, rating, comment.post_id, post_time, title, " +
                " post.content, post.apparea_id, comment_time, comment.content FROM comment " +
                " INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id WHERE comment.id = ?";

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            while (rs.next()) {
                comment = DAOUtil.commentFromResultSet(rs, "comment");
            }
        } catch (SQLException e) {
            LOG.error("SQL exception occurred");
            throw new DAOException(e);
        } finally {
            closeResources(connection, stmt, rs);
        }

        return comment;
    }

    /**
     *@see CommentDAO#getAll()
     */
    @Override
    public List<Comment> getAll() {
        Comment comment ;
        List<Comment> comments = new ArrayList<>();
        String query = " SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, avatar_url, rating, comment.post_id, post_time, title, " +
                " post.content, comment_time, comment.content FROM comment " +
                " INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id ";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query );
            while (rs.next()) {
                comment = DAOUtil.commentFromResultSet(rs, "comment");
                comments.add(comment);
            }
        } catch (SQLException e){
            LOG.error("SQL exception occurred");
            return null;
        } finally {
            closeResources(connection, stmt);
        }

        return comments;
    }
}


