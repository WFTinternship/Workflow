package com.workfront.internship.dao.impl;

import com.workfront.internship.app.App;
import com.workfront.internship.dao.PostDAO;
import com.workfront.internship.dao.UserDAO;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.dbConstants.DataBaseConstants;
import com.workfront.internship.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nane on 5/26/17.
 */
public class PostDAOImpl implements PostDAO {


    public boolean add(Post post) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String sql = "INSERT INTO post(user_id, apparea_id, date_time, title, content) " +
                " VALUE(?,?,?,?,?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, post.getUser().getId());
            stmt.setLong(2, post.getAppArea().getId());
            stmt.setString(3, dateFormat.format(date));
            stmt.setString(4, post.getTitle());
            stmt.setString(5, post.getContent());

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Post post) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Post> getAll() {
        List<Post> allPosts = new ArrayList<>();
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.rating, apparea_id, apparea.name, apparea.description, " +
                " apparea.team_id, team.name, team.office, date_time, title, content  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " JOIN apparea ON post.apparea_id = apparea.id " +
                " JOIN team ON apparea.team_id = team.id " +
                " WHERE post_id IS NULL";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()){
                Post post = new Post();
                post = fromResultSet(post, rs);
                allPosts.add(post);
            }

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return allPosts;
        } finally {
            close(rs);
        }
        return allPosts;
    }

    @Override
    public List<Post> getByUserId(long userId) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT (id, user_id, apparea_id, date_time, title, content) FROM post " +
                " WHERE post_id = NULL AND user_id = ?";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()){
                Post post = new Post();

                posts.add(post);
            }

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return posts;
        } finally {
            close(rs);
        }
        return posts;
    }

    @Override
    public List<Post> getByTitle(String title) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT (id, user_id, apparea_id, date_time, title, content) FROM post " +
                " WHERE post_id = NULL AND title LIKE %?%";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title.trim());
            rs = stmt.executeQuery();
            while (rs.next()){
                Post post = new Post();
                posts.add(post);
            }

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return posts;
        } finally {
            close(rs);
        }
        return posts;
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

    public static Post fromResultSet(Post post, ResultSet rs){
        try {
            post.setId(rs.getLong(rs.findColumn(DataBaseConstants.Post.id)));


            User user = new User();
            user = UserDAOImpl.fromResultSet(user, rs);
            post.setUser(user);

            AppArea appArea = AppArea.getById(
                    rs.getLong(rs.findColumn(DataBaseConstants.AppArea.id)));

            post.setAppArea(appArea);

            post.setPostTime(rs.getString(rs.findColumn(DataBaseConstants.Post.dateTime)));
            post.setTitle(rs.getString(rs.findColumn(DataBaseConstants.Post.title)));
            post.setContent(rs.getString(rs.findColumn(DataBaseConstants.Post.content)));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return post;
    }
}
