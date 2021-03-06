package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.AnswerRowMapper;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.PostRowMapper;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.UserRowMapper;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

/**
 * Created by nane on 6/14/17
 */

@Repository
public class PostDAOSpringImpl extends AbstractDao implements PostDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOSpringImpl.class);

    public PostDAOSpringImpl() {
        dataSource = DBHelper.getPooledConnection();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostDAOSpringImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * @see PostDAO#add(Post)
     */
    @Override
    public long add(Post post) {
        long id;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO post (user_id, apparea_id,post_id," +
                " post_time, title, content) VALUE(?,?,?,?,?,?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        sql, new String[]{"id"});
                ps.setLong(1, post.getUser().getId());
                ps.setLong(2, post.getAppArea().getId());
                if (post.getPost() == null) {
                    ps.setNull(3, Types.BIGINT);
                } else {
                    ps.setLong(3, post.getPost().getId());
                }
                ps.setTimestamp(4, post.getPostTime());
                ps.setString(5, post.getTitle());
                ps.setString(6, post.getContent());
                return ps;
            }, keyHolder);
            id = keyHolder.getKey().longValue();
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
        post.setId(id);
        return id;
    }

    /**
     * @see PostDAO#getAll()
     */
    @Override
    public List<Post> getAll() {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post_id IS NULL" +
                " ORDER BY post_time DESC";
        try {
            return jdbcTemplate.query(sql, new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }


    /**
     * @return retrieves all posts for specified page
     */
    public List<Post> getPostsByPage(long rowNumber) {
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
        try {
            return jdbcTemplate.query(sql, new Object[]{rowNumber}, new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getByUserId(long)
     */
    @Override
    public List<Post> getByUserId(long userId) {
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                " apparea.description, apparea.team_name, post_time, title, content, answer_id " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN best_answer ON post.id = best_answer.post_id " +
                " WHERE post.post_id IS NULL AND post.user_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{userId},
                    new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getByAppAreaId(long)
     */
    @Override
    public List<Post> getByAppAreaId(long id) {
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                " apparea.description, apparea.team_name, post_time, title, content, answer_id " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN best_answer ON post.id = best_answer.post_id " +
                " WHERE post.post_id IS NULL AND post.apparea_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{id}, new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getByTitle(String)
     */
    @Override
    public List<Post> getByTitle(String title) {
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode," +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post_id IS NULL AND post.title LIKE ? ";
        try {
            return jdbcTemplate.query(sql, new Object[]{"%" + title + "%"},
                    new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getById(long)
     */
    @Override
    public Post getById(long id) {
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
        try {
            return (Post) jdbcTemplate.queryForObject(sql, new Object[]{id},
                    new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getAnswersByPostId(long)
     */
    @Override
    public List<Post> getAnswersByPostId(long postId) {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title," +
                " content as answer_content " +
                " FROM post JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.post_id = ?" +
                " ORDER BY answer_time DESC";
        try {
            return jdbcTemplate.query(sql, new Object[]{postId},
                    new AnswerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result DataAccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    @Override
    public List<Post> getAnswersByUserId(long userId) {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title," +
                " content as answer_content " +
                " FROM post JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.post_id IS NOT NULL AND post.user_id = ?" +
                " ORDER BY answer_time DESC";
        try {
            return jdbcTemplate.query(sql, new Object[]{userId},
                    new AnswerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data Access Exception");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getBestAnswer(long)
     */
    @Override
    public Post getBestAnswer(long postId) {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title, " +
                " content as answer_content " +
                " FROM best_answer JOIN post ON best_answer.answer_id = post.id " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE  best_answer.post_id = ?";
        try {
            return (Post) jdbcTemplate.queryForObject(sql, new Object[]{postId},
                    new AnswerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getLikesNumber(long)
     */
    @Override
    public long getLikesNumber(long postId) {
        String sql = "SELECT COUNT(user_id) " +
                "FROM user_post_likes " +
                "WHERE post_id = ? ";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{postId}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getDislikesNumber(long)
     */
    @Override
    public long getDislikesNumber(long postId) {
        String sql = "SELECT COUNT(user_id) " +
                "FROM user_post_dislikes " +
                "WHERE post_id = ? ";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{postId}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#setBestAnswer(long, long)
     */
    @Override
    public void setBestAnswer(long postId, long answerId) {
        String sql = "INSERT INTO best_answer(post_id, answer_id) VALUE (?,?)";
        try {
            jdbcTemplate.update(sql, postId, answerId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#update(Post)
     */
    @Override
    public void update(Post post) {
        String sql = "UPDATE post SET title = ?, content = ? " +
                " WHERE post.id = ? ";
        try {
            jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getId());
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#like(long, long)
     */
    @Override
    public void like(long userId, long postId) {
        String sql = "INSERT INTO  user_post_likes (user_id, post_id) " +
                "VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, userId, postId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#dislike(long, long)
     */
    @Override
    public void dislike(long userId, long postId) {
        String sql = "INSERT INTO  user_post_dislikes (user_id, post_id) " +
                "VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, userId, postId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#removeLike(long, long)
     */
    @Override
    public void removeLike(long userId, long postId) {
        String sql = "DELETE FROM  user_post_likes " +
                " WHERE user_id = ? AND post_id = ?";
        try {
            jdbcTemplate.update(sql, userId, postId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#removeDislike(long, long)
     */
    @Override
    public void removeDislike(long userId, long postId) {
        String sql = "DELETE FROM  user_post_dislikes " +
                " WHERE user_id = ? AND post_id = ?";
        try {
            jdbcTemplate.update(sql, userId, postId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#delete(long)
     */
    @Override
    public void delete(long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#removeBestAnswer(long)
     */
    @Override
    public void removeBestAnswer(long answerId) {
        String sql = "DELETE FROM best_answer " +
                " WHERE answer_id = ?";
        try {
            jdbcTemplate.update(sql, answerId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getNumberOfAnswers(long)
     */
    @Override
    public Integer getNumberOfAnswers(long postId) {
        String sql = "SELECT COUNT(*) AS num FROM post WHERE post_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{postId},
                    Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }

    }

    /**
     * @see PostDAO#getNotified(long, long)
     */
    @Override
    public void getNotified(long postId, long userId) {
        String sql = "INSERT INTO notification (post_id, user_id) " +
                " VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, postId, userId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getNotificationRecipients(long)
     */
    @Override
    public List<User> getNotificationRecipients(long postId) {
        String sql = "SELECT * FROM user " +
                "WHERE id IN (SELECT user_id FROM notification WHERE post_id = ?) ";
        try {
            return jdbcTemplate.query(sql, new Object[]{postId}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }
}
