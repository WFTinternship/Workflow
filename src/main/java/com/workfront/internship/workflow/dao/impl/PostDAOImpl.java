package com.workfront.internship.workflow.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.ConnectionType;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nane on 5/26/17
 */
public class PostDAOImpl extends AbstractDao implements PostDAO {

    private static final Logger LOG = Logger.getLogger(PostDAOImpl.class);

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

    public PostDAOImpl() {
           dataSource = DBHelper.getPooledConnection();
    }

    /**
     * @see PostDAO#add(Post) ()
     *
     * @return
     */
    public long add(Post post) {
        long id = 0;
        String sql = "INSERT INTO post (user_id, apparea_id,post_id," +
                " post_time, title, content) VALUE(?,?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection();
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
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);

           rs = stmt.executeQuery();
            if (rs.next()){
                post = new Post();
                post = fromResultSet(post, rs);
            }
        }catch (SQLException e){
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        }finally {
            close(rs);
        }
        return post;
    }

    /**
     * @see PostDAO#getAll()
     *
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
                " WHERE post_id IS NULL" +
                " ORDER BY post_time DESC";
        ResultSet rs = null;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()){
                Post post = new Post();
                post = fromResultSet(post, rs);
                allPosts.add(post);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            close(rs);
        }
        return allPosts;
    }

    /**
     * @see PostDAO#getByUserId(long) (long) ()
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
        try (Connection conn = dataSource.getConnection();
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
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            close(rs);
        }
        return posts;
    }

    /**
     * @see PostDAO#getByTitle(String) ()
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
        try (Connection conn = dataSource.getConnection();
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
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            close(rs);
        }
        return posts;
    }

    /**
     * @see PostDAO#getAnswersByPostId(long)
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
                " WHERE post.post_id = ?" +
                " ORDER BY answer_time DESC";
        ResultSet rs = null;
        try (Connection conn = dataSource.getConnection();
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
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            close(rs);
        }
        return answerList;
    }

    /**
     * @see PostDAO#getBestAnswer(long)
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
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            if (rs.next()){
                bestAnswer = new Post();
                bestAnswer = answerFromResultSet(bestAnswer, rs);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            close(rs);
        }
        return bestAnswer;
    }

    /**
     * @see PostDAO#setBestAnswer(long, long)
     */
    @Override
    public void setBestAnswer(long postId, long answerId) {
        final String sql = "INSERT INTO best_answer(post_id, answer_id) VALUE (?,?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, postId);
            stmt.setLong(2, answerId);
            stmt.execute();

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("Foreign Key constraint fails");
        }
    }

    /**
     * @see PostDAO#update(Post) ()
     */
    @Override
    public void update(Post post) {
        String sql = "UPDATE post SET title = ?, content = ? " +
                " WHERE post.id = ? ";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setLong(3, post.getId());

            stmt.execute();

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        }
    }

    /**
     * @see PostDAO#delete(long) (Post) ()
     */
    @Override
    public void delete(long id) {
        int numberOfRowsAffected = 0;
        String sql = "DELETE FROM post WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);

            numberOfRowsAffected = stmt.executeUpdate();

        } catch (SQLException e) {
            LOG.error("SQL exception has occurred");
        }
        if (numberOfRowsAffected == 0){
            LOG.info("No rows affected");
        }
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
