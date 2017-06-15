package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.UserRowMapper;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vahag on 6/15/2017
 */
public class UserDAOSpringImpl extends AbstractDao implements UserDAO {

    public static final String id = "id";
    public static final String firstName = "first_name";
    public static final String lastName = "last_name";
    public static final String email = "email";
    public static final String password = "passcode";
    public static final String avatarURl = "avatar_url";
    public static final String rating = "rating";

    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    public UserDAOSpringImpl() {
        dataSource = DBHelper.getPooledConnection();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserDAOSpringImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * @see UserDAO#add(User)
     */
    @Override
    public long add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO  user (first_name, last_name, email, passcode, avatar_url, rating) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    sql, new String[] { "id" });
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getAvatarURL());
            ps.setInt(6, user.getRating());
            return ps;
        }, keyHolder);
        long id = keyHolder.getKey().longValue();
        user.setId(id);
        return id;
    }

    /**
     * @see UserDAO#subscribeToArea(long, long)
     */
    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        String sql = "INSERT INTO  user_apparea (user_id, apparea_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, appAreaId);
    }

    /**
     * @see UserDAO#unsubscribeToArea(long, long)
     */
    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        String sql = "DELETE FROM  user_apparea " +
                " WHERE user_id = ? AND apparea_id = ?";
        jdbcTemplate.update(sql, userId, appAreaId);
    }

    /**
     * @see UserDAO#getByName(String)
     */
    @Override
    public List<User> getByName(String name) {
        String filteredName = name.replaceAll(" ", "");
        String sql = "SELECT * " +
                "FROM  user " +
                "WHERE CONCAT (first_name, last_name) LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{filteredName + "%"}, new UserRowMapper());
    }

    /**
     * @see UserDAO#getById(long)
     */
    @Override
    public User getById(long id) {
        String sql = "SELECT * FROM  user " +
                "WHERE id = ?";
        return (User) jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
    }

    /**
     * @see UserDAO#getByEmail(String)
     */
    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM user " +
                "WHERE user.email = ?";
        return (User) jdbcTemplate.queryForObject(sql, new Object[]{email}, new UserRowMapper());
    }

    /**
     * @see UserDAO#getAppAreasById(long)
     */
    @Override
    public List<AppArea> getAppAreasById(long userId) {
        String sql = "SELECT * FROM  user_apparea " +
                "WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> AppArea.getById(userId));
    }

    /**
     * @see UserDAO#deleteById(long)
     */
    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM  user " +
                "WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    /**
     * @see UserDAO#deleteAll()
     */
    @Override
    public void deleteAll() {
        String sql = "DELETE FROM  user ";
        jdbcTemplate.update(sql);
    }
}
