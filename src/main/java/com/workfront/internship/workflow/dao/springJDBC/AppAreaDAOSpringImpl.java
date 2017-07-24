package com.workfront.internship.workflow.dao.springJDBC;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.dao.springJDBC.rowmappers.UserRowMapper;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import com.workfront.internship.workflow.exceptions.dao.NotExistingAppAreaException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nane on 6/15/17
 */
@Repository
public class AppAreaDAOSpringImpl extends AbstractDao implements AppAreaDAO {

    private static final Logger LOGGER = Logger.getLogger(AppAreaDAOSpringImpl.class);

    public AppAreaDAOSpringImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * @see AppAreaDAO#add(AppArea)
     */
    @Override
    public long add(AppArea appArea) {
        String sql = "INSERT INTO apparea (id, name, description, team_name) " +
                "VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, appArea.getId(), appArea.getName(),
                    appArea.getDescription(), appArea.getTeamName());
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
        return appArea.getId();
    }

    /**
     * @see AppAreaDAO#getUsersById(long)
     */
    @Override
    public List<User> getUsersById(long appAreaId) {
        String sql = "SELECT * FROM user " +
                "WHERE id IN (SELECT user_id FROM user_apparea WHERE apparea_id = ?) ";
        try {
            return jdbcTemplate.query(sql, new Object[]{appAreaId}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new DAOException(e);
        }
    }

    /**
     * @see AppAreaDAO#getById(long)
     */
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
    private Map<String, Object> getAppAreaFieldsById(long id){
        Map<String, Object> fieldsMap = new HashMap<>();
        final String sql = "SELECT * FROM apparea " +
                "WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                fieldsMap.put("Name",rs.getString(name));
                fieldsMap.put("TeamName",rs.getString(teamName));
                fieldsMap.put("Description",rs.getString(description));
            }
        } catch (SQLException e) {
            LOGGER.error("SQL exception occurred");
            throw new DAOException(e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        return fieldsMap;
    }

    private static boolean isTheActualAppArea(AppArea appArea, Map<String, Object> actualAppArea){
        return appArea.getName().equals(actualAppArea.get("Name")) &&
                appArea.getDescription().equals(actualAppArea.get("Description")) &&
                appArea.getTeamName().equals(actualAppArea.get("TeamName"));
    }

    /**
     * @see AppAreaDAO#deleteById(long)
     */
    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM apparea " +
                "WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e){
            throw new DAOException(e);
        }
    }
}
