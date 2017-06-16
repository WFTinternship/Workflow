package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.domain.Comment;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by Angel on 6/15/2017
 */
public class CommentDAOSpringImpl extends AbstractDao implements CommentDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public CommentDAOSpringImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public long add(Comment comment) {
        long id;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO comment(user_id,post_id,content,comment_time) "+
                "VALUE(?,?,?,?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        query, new String[]{"id"});
                ps.setObject(1, comment.getUser());
                ps.setObject(2, comment.getPost());
                ps.setString(3, comment.getContent());
                ps.setTimestamp(4, comment.getCommentTime());
                return ps;
            }, keyHolder);
            id = keyHolder.getKey().longValue();
        } catch (DataAccessException e){
            LOGGER.error("Data Access Exception");
            throw new RuntimeException(e);
        }
        comment.setId(id);
        return id;
    }
      @Override
    public Comment getById(long id) {
        return null;
    }

    @Override
    public List<Comment> getByPostId(long postId) {
        return null;
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public boolean update(long id, String newComment) {
        return false;
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}

