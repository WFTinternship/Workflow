package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nane on 5/26/17
 */
public class PostDAOImpl extends AbstractDao implements PostDAO {

    // Post fileds
    public static final String id = "id";
    public static final String postId = "post_id";
    public static final String appAreaId = "apparea_id";
    public static final String dateTime = "post_time";
    public static final String content = "postContent";
    public static final String isCorrect = "is_correct";
    private static final Logger LOG = Logger.getLogger(PostDAOImpl.class);
    public static String postTitle = "answerTitle";

    // Answer fields

    public static String answerTime = "answer_time";
    public static String answerContent = "answer_content";
    public static String userId = "user_id";
    public static String title = "answer_title";

    public PostDAOImpl() {
        dataSource = DBHelper.getPooledConnection();
    }

    public PostDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static Post answerFromResultSet(Post answer, ResultSet rs) {
        try {
            answer.setId(rs.getLong(id));

            User user = new User();
            user = UserDAOImpl.fromResultSet(rs);
            user.setId(rs.getLong(userId));
            answer.setUser(user);

            AppArea appArea = AppArea.getById(
                    rs.getLong(appAreaId));
            answer.setAppArea(appArea);
            answer.setPostTime(rs.getTimestamp(answerTime));
            answer.setTitle(rs.getString(title));
            answer.setContent(rs.getString(answerContent));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public static Post fromResultSet(Post post, ResultSet rs) {
        try {
            post.setId(rs.getLong(id));

            User user = UserDAOImpl.fromResultSet(rs);
            user.setId(rs.getLong(userId));
            post.setUser(user);

            AppArea appArea = AppArea.getById(
                    rs.getLong(appAreaId));

            post.setAppArea(appArea);
            post.setPostTime(rs.getTimestamp(dateTime));
            post.setTitle(rs.getString(postTitle));
            post.setContent(rs.getString(content));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    /**
     * @return
     * @see PostDAO#add(Post) ()
     */
    public long add(Post post) {
        long id = 0;
        String sql = "INSERT INTO post (user_id, apparea_id,post_id," +
                " post_time, title, content) VALUE(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, post.getUser().getId());
            stmt.setLong(2, post.getAppArea().getId());
            if (post.getPost() == null) {
                stmt.setNull(3, Types.BIGINT);
            } else {
                stmt.setLong(3, post.getPost().getId());
            }
            stmt.setTimestamp(4, post.getPostTime());
            stmt.setString(5, post.getTitle());
            stmt.setString(6, post.getContent());

            PreparedStatement st = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=0", Statement.RETURN_GENERATED_KEYS);
            st.execute();
            stmt.execute();
            PreparedStatement t = conn.prepareStatement("SET FOREIGN_KEY_CHECKS=1", Statement.RETURN_GENERATED_KEYS);
            t.execute();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
            }
            post.setId(id);

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException();
        } finally {
            closeResources(conn, stmt, rs);
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
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                "apparea.description,  apparea.team_name, post_time, title, content  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                post = new Post();
                post = fromResultSet(post, rs);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return post;
    }

    /**
     * @see PostDAO#getAll()
     */
    @Override
    public List<Post> getAll() {
        List<Post> allPosts = new ArrayList<>();
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post_id IS NULL" +
                " ORDER BY post_time DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post = fromResultSet(post, rs);
                allPosts.add(post);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
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
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                " apparea.description, apparea.team_name, post_time, title, content, answer_id " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN best_answer ON post.id = best_answer.post_id " +
                " WHERE post.post_id IS NULL AND post.user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post = fromResultSet(post, rs);
                posts.add(post);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return posts;
    }

    /**
     * @param id id of the app area
     * @see PostDAO#getByAppAreaId(long)
     */
    @Override
    public List<Post> getByAppAreaId(long id) {
        List<Post> posts = new ArrayList<>();
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                " apparea.description, apparea.team_name, post_time, title, content, answer_id " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN best_answer ON post.id = best_answer.post_id " +
                " WHERE post.post_id IS NULL AND post.apparea_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post = fromResultSet(post, rs);
                posts.add(post);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
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
                " user.email, user.avatar_url, user.rating, user.passcode," +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post_id IS NULL AND post.title LIKE ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + title.trim() + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post = fromResultSet(post, rs);
                posts.add(post);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return posts;
    }

    /**
     * @see PostDAO#getAnswersByPostId(long)
     */
    @Override
    public List<Post> getAnswersByPostId(long postId) {
        List<Post> answerList = new ArrayList<>();
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title," +
                " content as answer_content " +
                " FROM post JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.post_id = ?" +
                " ORDER BY answer_time DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Post answer = new Post();
                answer = answerFromResultSet(answer, rs);
                answerList.add(answer);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return answerList;
    }

    /**
     * @see PostDAO#getBestAnswer(long)
     */
    @Override
    public Post getBestAnswer(long postId) {
        Post bestAnswer = null;
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title, " +
                " content as answer_content " +
                " FROM best_answer JOIN post ON best_answer.answer_id = post.id " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE  best_answer.post_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                bestAnswer = new Post();
                bestAnswer = answerFromResultSet(bestAnswer, rs);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return bestAnswer;
    }

    /**
     * @see PostDAO#setBestAnswer(long, long)
     */
    @Override
    public void setBestAnswer(long postId, long answerId) {
        String sql = "INSERT INTO best_answer(post_id, answer_id) VALUE (?,?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, postId);
            stmt.setLong(2, answerId);
            stmt.execute();

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("Foreign Key constraint fails");
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#update(Post) ()
     */
    @Override
    public void update(Post post) {
        String sql = "UPDATE post SET title = ?, content = ? " +
                " WHERE post.id = ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setLong(3, post.getId());

            stmt.execute();

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new RuntimeException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#delete(long) (Post) ()
     */
    @Override
    public void delete(long id) {
        int numberOfRowsAffected = 0;
        String sql = "DELETE FROM post WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);

            numberOfRowsAffected = stmt.executeUpdate();

        } catch (SQLException e) {
            LOG.error("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
        if (numberOfRowsAffected == 0) {
            LOG.info("No rows affected");
        }
    }
}
