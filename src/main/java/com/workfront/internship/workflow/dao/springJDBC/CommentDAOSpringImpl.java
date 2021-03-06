package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.CommentRowMapper;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel on 6/15/2017
 */
@Repository
public class CommentDAOSpringImpl extends AbstractDao implements CommentDAO {

    private static final Logger LOGGER = Logger.getLogger(CommentDAOSpringImpl.class);

    public CommentDAOSpringImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * @see CommentDAO#add(Comment)
     */
    @Override
    public long add(Comment comment) {
        long id;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO comment(user_id,post_id,content,comment_time) " +
                "VALUE(?,?,?,?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        query, new String[]{"id"});
                ps.setLong(1, comment.getUser().getId());
                ps.setLong(2, comment.getPost().getId());
                ps.setString(3, comment.getContent());
                ps.setTimestamp(4, comment.getCommentTime());
                return ps;
            }, keyHolder);
            id = keyHolder.getKey().longValue();
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
        comment.setId(id);
        return id;
    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Override
    public Comment getById(long id) {
        String query = "SELECT comment.id, comment.user_id, user.first_name, user.last_name, " +
                " user.email, user.passcode, user.avatar_url, user.rating, comment.post_id," +
                " post.post_time, post.title,post.content,post.apparea_id, comment.comment_time," +
                "comment.content FROM comment INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id WHERE comment.id = ?";

        try {
            return (Comment) jdbcTemplate.queryForObject(query,
                    new Object[]{id}, new CommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @see CommentDAO#getByPostId(long)
     */
    @Override
    public List<Comment> getByPostId(long id) {
        String query = "SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, avatar_url, rating, comment.post_id, post_time, title, " +
                " post.content, apparea_id, comment_time, comment.content " +
                " FROM comment INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id WHERE comment.post_id = ?";
        try {
            return jdbcTemplate.query(query, new Object[]{id}, new CommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @see CommentDAO#getAll()
     */
    @Override
    public List<Comment> getAll() {
        String query = " SELECT comment.id, comment.user_id, first_name, last_name, " +
                " email, passcode, avatar_url, rating, comment.post_id, post_time, title, " +
                " post.content, post.apparea_id, comment_time, comment.content FROM comment " +
                " INNER JOIN user ON comment.user_id = user.id " +
                " INNER JOIN post ON comment.post_id = post.id ";
        try {
            return jdbcTemplate.query(query, new CommentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @see CommentDAO#update(long, String)
     */
    @Override
    public boolean update(long id, String newContent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String query = "UPDATE comment SET content = ?, " +
                " comment_time = ? " +
                " WHERE comment.id = ?";
        try {
            jdbcTemplate.update(query, newContent, dateFormat.format(date), id);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
        return true;
    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Override
    public void delete(long id) {
        String query = "DELETE FROM  comment " +
                "WHERE id = ?";
        try {
            jdbcTemplate.update(query, id);
        } catch (DataAccessException e) {
            LOGGER.error("Data Access Exception");
            throw new DAOException(e);
        }
    }
}

