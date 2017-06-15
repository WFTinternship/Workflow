package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.dao.NotExistingAppAreaException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    @Override
    public long add(AppArea appArea) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO apparea (id, name, description, team_name) " +
                "VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, appArea.getId(), appArea.getName(),
                appArea.getDescription(), appArea.getTeamName());
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<User> getUsersById(long appAreaId) {
        String sql = "SELECT * FROM user " +
                "WHERE id IN (SELECT user_id FROM user_apparea WHERE apparea_id = ?) ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserDAOImpl().fromResultSet(rs));
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

    }
}
