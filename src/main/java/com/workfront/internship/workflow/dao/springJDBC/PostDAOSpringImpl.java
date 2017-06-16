package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.DBHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by nane on 6/14/17
 */
public class PostDAOSpringImpl extends AbstractDao implements PostDAO {

    // Post fileds
    public static final String id = "id";
    public static final String postId = "post_id";
    public static final String appAreaId = "apparea_id";
    public static final String dateTime = "post_time";
    public static final String content = "content";
    public static final String isCorrect = "is_correct";
    public static String postTitle = "title";

    // Answer fields

    public static String answerTime = "answer_time";
    public static String answerContent = "answer_content";
    public static String userId = "user_id";
    public static String title = "answer_title";

    public PostDAOSpringImpl() {
        dataSource = DBHelper.getPooledConnection();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public PostDAOSpringImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static Post fromResultSet(ResultSet rs) {
        Post post = new Post();
        try {
            post.setId(rs.getLong(id));

            AppArea appArea = AppArea.getById(
                    rs.getLong(AppAreaDAOImpl.id));
            post.setAppArea(appArea);

            User user = new User();
            user = UserDAOImpl.fromResultSet(rs);
            user.setId(rs.getLong(userId));
            post.setUser(user);

            post.setPostTime(rs.getTimestamp(dateTime));
            post.setTitle(rs.getString(postTitle));
            post.setContent(rs.getString(content));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static Post answerFromResultSet(ResultSet rs) {
        Post answer = new Post();
        try {
            answer.setId(rs.getLong(id));

            AppArea appArea = AppArea.getById(
                    rs.getLong(id));
            answer.setAppArea(appArea);

            User user = new User();
            user = UserDAOImpl.fromResultSet(rs);
            user.setId(rs.getLong(userId));
            answer.setUser(user);

            answer.setPostTime(rs.getTimestamp(answerTime));
            answer.setTitle(rs.getString(title));
            answer.setContent(rs.getString(answerContent));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
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
            throw new RuntimeException(e);
        }
        post.setId(id);
        return id;
    }

    @Override
    public List<Post> getAll() {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, apparea.description, " +
                " apparea.team_name, post_time, title, content  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post_id IS NULL" +
                " ORDER BY post_time DESC";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> fromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
            return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> fromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
            return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> fromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
            return jdbcTemplate.query(sql, new Object[]{"%" + title + "%"}, (rs, rowNum) -> fromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Post getById(long id) {
        String sql = "SELECT post.id, user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, apparea_id, apparea.name, " +
                "apparea.description,  apparea.team_name, post_time, title, content  " +
                " FROM post " +
                " JOIN user ON post.user_id = user.id " +
                " LEFT JOIN apparea ON post.apparea_id = apparea.id " +
                " WHERE post.id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> fromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
            return jdbcTemplate.query(sql, new Object[]{postId}, (rs, rowNum) -> fromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
            return jdbcTemplate.queryForObject(sql, new Object[]{postId},
                    (rs, rowNum) -> answerFromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
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
    public void delete(long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e){
            throw new RuntimeException(e);
        }
    }
}
