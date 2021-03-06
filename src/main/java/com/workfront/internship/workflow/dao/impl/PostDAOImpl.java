package com.workfront.internship.workflow.dao.impl;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nane on 5/26/17
 */
@Repository
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

    /**
     * @see PostDAO#add(Post)
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
            throw new DAOException();
        } finally {
            closeResources(conn, stmt, rs);
        }
        return post.getId();
    }

    /**
     * @see PostDAO#getById(long)
     */
    @Override
    public Post getById(long id) {
        Post post = null;
        String sql = "SELECT post.id, post.user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, post.apparea_id, apparea.name, " +
                " apparea.description,  apparea.team_name, post.post_time, post.title, post.content, " +
                " PARENT.id AS parentId, PARENT.user_id AS parentUserId, " +
                " PARENT_USER.first_name AS parentUserFirstName, PARENT_USER.last_name AS parentUserLastName, " +
                " PARENT_USER.email AS parentUserEmail, PARENT_USER.passcode AS parentUserPasscode, " +
                " PARENT_USER.avatar_url AS parentUserAvatar, PARENT_USER.rating AS parentUserRating, " +
                " PARENT.apparea_id AS parentAppAreaId, " +
                " PARENT.post_time AS parentTime, " +
                " PARENT.title AS parentTitle, PARENT.content AS parentContent " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN post AS PARENT on post.post_id = PARENT.id " +
                " LEFT JOIN user AS PARENT_USER ON PARENT.user_id = PARENT_USER.id " +
                " LEFT JOIN apparea AS PARENT_APPAREA ON PARENT.apparea_id = PARENT_APPAREA.id " +
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
                post = DAOUtil.postFromResultSet(rs);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
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
                Post post;
                post = DAOUtil.postFromResultSet(rs);
                allPosts.add(post);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return allPosts;
    }

    /**
     * @see PostDAO#getPostsByPage(long)
     */
    @Override
    public List<Post> getPostsByPage(long rowNumber) {
        List<Post> allPosts = new ArrayList<>();
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post_id IS NULL " +
                " ORDER BY post_time DESC " +
                " LIMIT  ?,5 ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, rowNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Post post;
                post = DAOUtil.postFromResultSet(rs);
                allPosts.add(post);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
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
                post = DAOUtil.postFromResultSet(rs);
                posts.add(post);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return posts;
    }

    /**
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
                post = DAOUtil.postFromResultSet(rs);
                posts.add(post);
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return posts;
    }

    /**
     * @see PostDAO#getByTitle(String)
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
                Post post;
                post = DAOUtil.postFromResultSet(rs);
                posts.add(post);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
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
                Post answer;
                answer = DAOUtil.answerFromResultSet(rs);
                answerList.add(answer);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return answerList;
    }

    @Override
    public List<Post> getAnswersByUserId(long userId) {
        List<Post> answerList = new ArrayList<>();
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title," +
                " content as answer_content " +
                " FROM post JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.post_id IS NOT NULL AND post.user_id = ?" +
                " ORDER BY answer_time DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Post answer;
                answer = DAOUtil.answerFromResultSet(rs);
                answerList.add(answer);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
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
                bestAnswer = DAOUtil.answerFromResultSet(rs);
            }

        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return bestAnswer;
    }

    /**
     * @see PostDAO#getLikesNumber(long)
     */
    @Override
    public long getLikesNumber(long postId) {
        String sql = "SELECT COUNT(user_id) AS count " +
                "FROM user_post_likes " +
                "WHERE post_id = ? ";

        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        long likesNumber = 0;

        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                likesNumber = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return likesNumber;
    }

    /**
     * @see PostDAO#getDislikesNumber(long)
     */
    @Override
    public long getDislikesNumber(long postId) {
        String sql = "SELECT COUNT(user_id) AS count " +
                "FROM user_post_dislikes " +
                "WHERE post_id = ? ";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        long dislikesNumber = 0;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                dislikesNumber = rs.getInt("count");
            }
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt, rs);
        }
        return dislikesNumber;
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
            throw new DAOException("Foreign Key constraint fails");
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#update(Post)
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
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#like(long, long)
     */
    @Override
    public void like(long userId, long postId) {
        String sql = "INSERT INTO  user_post_likes (user_id, post_id) " +
                "VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, postId);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("SQL exception has occurred");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#dislike(long, long)
     */
    @Override
    public void dislike(long userId, long postId) {
        String sql = "INSERT INTO  user_post_dislikes (user_id, post_id) " +
                "VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, postId);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("SQL exception has occurred.");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#removeLike(long, long)
     */
    @Override
    public void removeLike(long userId, long postId) {
        String sql = "DELETE FROM  user_post_likes " +
                " WHERE user_id = ? AND post_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException(e);
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#removeDislike(long, long)
     */
    @Override
    public void removeDislike(long userId, long postId) {
        String sql = "DELETE FROM  user_post_dislikes " +
                " WHERE user_id = ? AND post_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException(e);
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#delete(long)
     */
    @Override
    public void delete(long id) {
        int numberOfRowsAffected;
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
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
        if (numberOfRowsAffected == 0) {
            LOG.info("No rows affected");
        }
    }

    /**
     * @see PostDAO#removeBestAnswer(long)
     */
    @Override
    public void removeBestAnswer(long answerId) {
        String sql = "DELETE FROM best_answer " +
                " WHERE answer_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, answerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("SQL exception has occurred");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
    }

    /**
     * @see PostDAO#getNumberOfAnswers(long)
     */
    @Override
    public Integer getNumberOfAnswers(long postId) {
        String sql = "SELECT COUNT(*) AS num FROM post WHERE post_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        Integer numOfAnswers = 0;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, postId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                numOfAnswers = rs.getInt("num");
            }
        } catch (SQLException e) {
            LOG.error("SQL exception has occurred");
            throw new DAOException("SQL exception has occurred");
        } finally {
            closeResources(conn, stmt);
        }
        return numOfAnswers;
    }

    /**
     * @see PostDAO#getNotified(long, long)
     */
    @Override
    public void getNotified(long postId, long userId) {
        String sql = "INSERT INTO  notification (post_id, user_id) " +
                " VALUES (?, ?)";
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = dataSource.getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setLong(1, postId);
            stmt.setLong(2, userId);
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("SQL exception");
            throw new DAOException(e);
        } finally {
            closeResources(con, stmt);
        }
    }

    /**
     * @param postId
     * @return
     * @see PostDAO#getNotificationRecipients(long)
     */
    @Override
    public List<User> getNotificationRecipients(long postId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user " +
                "WHERE id IN (SELECT user_id FROM notification WHERE post_id = ?) ";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(DAOUtil.userFromResultSet(rs));
            }
        } catch (SQLException e) {
            LOG.error("SQL exception occurred");
            throw new DAOException(e);
        } finally {
            closeResources(conn, stmt);
        }
        return users;
    }
}
