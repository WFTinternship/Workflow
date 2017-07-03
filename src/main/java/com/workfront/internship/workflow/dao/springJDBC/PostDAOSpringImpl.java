package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.AnswerRowMapper;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.PostRowMapper;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.UserRowMapper;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

/**
 * Created by nane on 6/14/17
 */

@Component
public class PostDAOSpringImpl extends AbstractDao implements PostDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public PostDAOSpringImpl() {
        dataSource = DBHelper.getPooledConnection();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostDAOSpringImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
            throw new RuntimeException(e);
        }
        post.setId(id);
        return id;
    }

    @Override
    public List<Post> getAll() {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content, likes_number, dislikes_number  " +
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getByUserId(long userId) {
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                " apparea.description, apparea.team_name, post_time, title, content, answer_id, likes_number, dislikes_number " +
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getByAppAreaId(long id) {
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                " apparea.description, apparea.team_name, post_time, title, content, answer_id, likes_number, dislikes_number " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " LEFT JOIN best_answer ON post.id = best_answer.post_id " +
                " WHERE post.post_id IS NULL AND post.apparea_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{id},
                    new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getByTitle(String title) {
        String sql = " SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode," +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content, likes_number, dislikes_number " +
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getById(long id) {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                "apparea.description,  apparea.team_name, post_time, title, content, likes_number, dislikes_number  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.id = ?";
        try {
            return (Post) jdbcTemplate.queryForObject(sql, new Object[]{id},
                    new PostRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getAnswersByPostId(long postId) {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title," +
                " content as answer_content, likes_number, dislikes_number " +
                " FROM post JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.post_id = ?" +
                " ORDER BY answer_time DESC";
        try {
            return jdbcTemplate.query(sql, new Object[]{postId},
                    new AnswerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Empty Result Data AccessException");
            return null;
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getBestAnswer(long postId) {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.avatar_url, user.rating, user.passcode, " +
                " apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time as answer_time, title as answer_title, " +
                " content as answer_content, likes_number, dislikes_number " +
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setBestAnswer(long postId, long answerId) {
        String sql = "INSERT INTO best_answer(post_id, answer_id) VALUE (?,?)";
        jdbcTemplate.update(sql, postId, answerId);
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE post SET title = ?, content = ? " +
                " WHERE post.id = ? ";
        jdbcTemplate.update(sql, post.getTitle(), post.getContent(), post.getId());

    }

    @Override
    public void like(long id) {
        Post post = getById(id);
        long likesNumber = post.getLikesNumber();
        likesNumber += 1;
        String sql = "UPDATE post SET likes_number = ? " +
                " WHERE post.id = ? ";
        jdbcTemplate.update(sql, likesNumber, post.getId());
    }

    @Override
    public void dislike(long id) {
        Post post = getById(id);
        long dislikesNumber = post.getDislikesNumber();
        dislikesNumber += 1;
        String sql = "UPDATE post SET dislikes_number = ? " +
                " WHERE post.id = ? ";
        jdbcTemplate.update(sql, dislikesNumber, post.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getNumberOfAnswers(long postId) {
        String sql = "SELECT COUNT(*) AS num FROM post WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{postId},
                Integer.class);
    }

    @Override
    public void getNotified(long postId, long userId) {
        String sql = "INSERT INTO notification (post_id, user_id) " +
                " VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql, postId, userId);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new RuntimeException(e);
        }
    }

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
            throw new RuntimeException(e);
        }
    }
}
