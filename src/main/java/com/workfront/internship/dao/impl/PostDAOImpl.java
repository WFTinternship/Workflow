package com.workfront.internship.dao.impl;

import com.workfront.internship.app.App;
import com.workfront.internship.dao.PostDAO;
import com.workfront.internship.dao.UserDAO;
import com.workfront.internship.dataModel.AppArea;
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
import java.util.concurrent.RunnableFuture;

/**
 * Created by nane on 5/26/17.
 */
public class PostDAOImpl implements PostDAO {

    /**
     * @see PostDAO#add(Post) ()
     *
     * @return
     */
    public long add(Post post) {
        long id = 0;
        String sql = "INSERT INTO work_flow.post (user_id, apparea_id, post_time, title, content) " +
                " VALUE(?,?,?,?,?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, post.getUser().getId());
            stmt.setLong(2, post.getAppArea().getId());
            stmt.setTimestamp(3, post.getPostTime());
            stmt.setString(4, post.getTitle());
            stmt.setString(5, post.getContent());

            PreparedStatement st = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0", Statement.RETURN_GENERATED_KEYS);
            st.execute();
            stmt.execute();
            PreparedStatement t = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1",Statement.RETURN_GENERATED_KEYS);
            t.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                id = rs.getLong(1);
            }
            post.setId(id);

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return post.getId();
    }

    /**
     * @see PostDAO#getById(long) (Post) ()
     *
     * @return
     */
    @Override
    public Post getById(long id) {
        Post post = null;
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.rating, apparea_id, apparea.name, " +
                "apparea.description,  apparea.team_name, post_time, title, content, answer_id  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN best_answer ON post.id = best_answer.post_id " +
                " WHERE post.id = ?";
        ResultSet rs = null;
        try(Connection conn = DBHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);

           rs = stmt.executeQuery();
            if (rs.next()){
                post = new Post();
                post = fromResultSet(post, rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close(rs);
        }
        return post;
    }

    /**
     * @see PostDAO#getAll()
     *
     * @return
     */
    @Override
    public List<Post> getAll() {
        List<Post> allPosts = new ArrayList<>();
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.rating, apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
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

        } catch (SQLException e) {
            e.printStackTrace();
            return allPosts;
        } finally {
            close(rs);
        }
        return allPosts;
    }

    /**
     * @see PostDAO#getByUserId(long) (long) ()
     *
     * @return
     */
    @Override
    public List<Post> getByUserId(long userId) {
        List<Post> posts = new ArrayList<>();
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.rating, apparea_id, apparea.name, " +
                " apparea.description, apparea.team_name, post_time, title, content, answer_id " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN best_answer ON post.id = best_answer.post_id " +
                " WHERE post.post_id IS NULL AND post.user_id = ?";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()){
                Post post = new Post();
                post = fromResultSet(post, rs);
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

    /**
     * @see PostDAO#getByTitle(String) ()
     *
     * @return
     */
    @Override
    public List<Post> getByTitle(String title) {
        List<Post> posts = new ArrayList<>();
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.rating, user.passcode," +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post_id IS NULL AND post.title LIKE ? ";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + title.trim() + "%");
            rs = stmt.executeQuery();
            while (rs.next()){
                Post post = new Post();
                post = fromResultSet(post, rs);
                posts.add(post);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return posts;
        } finally {
            close(rs);
        }
        return posts;
    }

    /**
     * @see PostDAO#update(Post) ()
     *
     * @return
     */
    @Override
    public boolean update(Post post) {
        String sql = "UPDATE post SET title = ?, content = ? " +
                " WHERE post.id = ? ";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, post.getTitle().trim());
            stmt.setString(2, post.getContent().trim());
            stmt.setLong(3, post.getId());

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @see PostDAO#delete(long) (Post) ()
     *
     * @return
     */
    @Override
    public int delete(long id) {
        int numberOfRowsAffeced;
        String sql = "DELETE FROM post WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            numberOfRowsAffeced = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return numberOfRowsAffeced;
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
            post.setId(rs.getLong(DataBaseConstants.Post.id));

            User user = new User();
            user = UserDAOImpl.fromResultSet(user, rs);
            user.setId(rs.getLong(DataBaseConstants.Post.userId));
            post.setUser(user);

            AppArea appArea = AppArea.getById(
                    rs.getLong(DataBaseConstants.AppArea.id));

            post.setAppArea(appArea);

            post.setPostTime(rs.getTimestamp(DataBaseConstants.Post.dateTime));
            post.setTitle(rs.getString(DataBaseConstants.Post.title));
            post.setContent(rs.getString(DataBaseConstants.Post.content));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return post;
    }
}
