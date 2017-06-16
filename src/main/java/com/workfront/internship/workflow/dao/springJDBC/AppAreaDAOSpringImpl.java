package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.dao.NotExistingAppAreaException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nane on 6/15/17
 */
public class AppAreaDAOSpringImpl extends AbstractDao implements AppAreaDAO {

    public static final String id = "id";
    public static final String name = "name";
    public static final String description = "description";
    public static final String teamName = "team_name";

    public AppAreaDAOSpringImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long add(AppArea appArea) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO apparea (id, name, description, team_name) " +
                "VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        sql, new String[]{"id"});
                ps.setLong(1, appArea.getId());
                ps.setString(2, appArea.getName());
                ps.setString(3, appArea.getDescription());
                ps.setString(4, appArea.getTeamName());
                return ps;
            }, keyHolder);
        }catch (DataAccessException e){
            throw new RuntimeException(e);
        }
        return appArea.getId();
    }

    @Override
    public List<User> getUsersById(long appAreaId) {
        String sql = "SELECT * FROM user " +
                "WHERE id IN (SELECT user_id FROM user_apparea WHERE apparea_id = ?) ";
        try {
            return jdbcTemplate.query(sql, new Object[]{appAreaId}, (rs, rowNum) -> new UserDAOImpl().fromResultSet(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AppArea getById(long id) {
        AppArea appArea = AppArea.getById(id);
        Map<String, Object> actualAppArea = getAppAreaFieldsById(id);
        if (actualAppArea.isEmpty()) {
            return null;
        }
        if (!isTheActualAppArea(appArea, actualAppArea)) {
            throw new NotExistingAppAreaException();
        }
        return appArea;
    }

    /**
     * Returns Map of appArea fields from database
     */
    private Map<String, Object> getAppAreaFieldsById(long id) {
        Map<String, Object> fieldsMap = new HashMap<>();
        String sql = "SELECT * FROM apparea " +
                "WHERE id = ?";

        return fieldsMap;
    }

    private static boolean isTheActualAppArea(AppArea appArea, Map<String, Object> actualAppArea){
        return appArea.getName().equals(actualAppArea.get("Name")) &&
                appArea.getDescription().equals(actualAppArea.get("Description")) &&
                appArea.getTeamName().equals(actualAppArea.get("TeamName"));
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM apparea " +
                "WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e){
            throw new RuntimeException(e);
        }
    }
}
