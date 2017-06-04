package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dataModel.AppArea;
import com.workfront.internship.workflow.dataModel.Post;
import com.workfront.internship.workflow.dataModel.User;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nane on 5/26/17. 
 */
public class PostDAOImpl implements PostDAO {

    private static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

    // Post fileds
    public static final String id = "id";
    public static final String postId = "post_id";
    public static final String appAreaId = "apparea_id";
    public static final String dateTime = "post_time";
    public static final String content  = "content";
    public static final String isCorrect  = "is_correct";
    public static String postTitle = "title";

    // Answer fields

    public static String answerTime = "answer_time";
    public static String answerContent = "answer_content";
    public static String userId = "user_id";
    public static String title = "answer_title";

    /**
     * @see PostDAO#add(Post) ()
     *
     * @return
     */
    public long add(Post post) {
        long id = 0;
        String sql = "INSERT INTO post (user_id, apparea_id,post_id," +
                " post_time, title, content) VALUE(?,?,?,?,?,?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, post.getUser().getId());
            stmt.setLong(2, post.getAppArea().getId());
            if (post.getPost() == null){
                stmt.setNull(3, Types.BIGINT);
            }else {
                stmt.setLong(3, post.getPost().getId());
            }
            stmt.setTimestamp(4, post.getPostTime());
            stmt.setString(5, post.getTitle());
            stmt.setString(6, post.getContent());

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
            LOG.error("SQL exception");
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
                "apparea.description,  apparea.team_name, post_time, title, content  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
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
            LOG.error("SQL exception");
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
            LOG.error("SQL exception");
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
            LOG.error("SQL exception");
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
            LOG.error("SQL exception");
            return posts;
        } finally {
            close(rs);
        }
        return posts;
    }

    /**
     * @see PostDAO#getAnswersByPostId(long)
     *
     * @param postId
     * @return
     */
    @Override
    public List<Post> getAnswersByPostId(long postId) {
        List<Post> answerList = new ArrayList<>();
        final String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title," +
                " content as answer_content " +
                " FROM post JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.post_id = ?";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            while (rs.next()){
                Post answer = new Post();
                answer = answerFromResultSet(answer, rs);
                answerList.add(answer);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            return answerList;
        } finally {
            close(rs);
        }
        return answerList;
    }

    /**
     * @see PostDAO#getBestAnswer(long)
     *
     * @param postId
     * @return
     */
    @Override
    public Post getBestAnswer(long postId) {
        Post bestAnswer = null;
        final String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title, " +
                " content as answer_content " +
                " FROM best_answer JOIN post ON best_answer.answer_id = post.id " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE  best_answer.post_id = ?";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            if (rs.next()){
                bestAnswer = new Post();
                bestAnswer = answerFromResultSet(bestAnswer, rs);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            return bestAnswer;
        } finally {
            close(rs);
        }
        return bestAnswer;
    }

    /**
     * @see PostDAO#setBestAnswer(long, long)
     *
     * @param postId
     * @param answerId
     * @return
     */
    @Override
    public boolean setBestAnswer(long postId, long answerId) {
        final String sql = "INSERT INTO best_answer(post_id, answer_id) VALUE (?,?)";
        ResultSet rs = null;
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, postId);
            stmt.setLong(2, answerId);
            stmt.execute();

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("Foreign Key constraint fails");
        } finally {
            close(rs);
        }
        return true;
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
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setLong(3, post.getId());

            stmt.execute();

        } catch (SQLException e) {
            LOG.error("SQL exception");
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
            LOG.error("SQL exception");
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


    public static Post answerFromResultSet(Post answer, ResultSet rs){
        try {
            answer.setId(rs.getLong(id));

            User user= new User();
            user = UserDAOImpl.fromResultSet(user, rs);
            user.setId(rs.getLong(userId));
            answer.setUser(user);

            AppArea appArea = AppArea.getById(
                    rs.getLong(id));
            answer.setAppArea(appArea);
            answer.setPostTime(rs.getTimestamp(answerTime));
            answer.setTitle(rs.getString(title));
            answer.setContent(rs.getString(answerContent));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public static Post fromResultSet(Post post, ResultSet rs){
        try {
            post.setId(rs.getLong(id));

            User user = new User();
            user = UserDAOImpl.fromResultSet(user, rs);
            user.setId(rs.getLong(userId));
            post.setUser(user);

            AppArea appArea = AppArea.getById(
                    rs.getLong(AppAreaDAOImpl.id));

            post.setAppArea(appArea);
            post.setPostTime(rs.getTimestamp(dateTime));
            post.setTitle(rs.getString(postTitle));
            post.setContent(rs.getString(content));

        }catch (SQLException e){
            e.printStackTrace();
        }
        return post;
    }
}
