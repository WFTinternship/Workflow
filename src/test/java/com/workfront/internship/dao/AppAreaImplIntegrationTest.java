package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DBHelper;
import com.workfront.internship.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppAreaImplIntegrationTest {

    private AppAreaDAO appAreaDAO;
    private UserDAO userDAO;
    private AppArea appArea;
    private User user;


    @Before
    public void setup(){
        appAreaDAO = new AppAreaDAOImpl();
        userDAO = new UserDAOImpl();
        appArea = AppArea.values()[0];
        user = DaoTestUtil.getRandomUser();
    }

    @After
    public void tearDown(){
        if(getAppAreaFieldsById(appArea.getId()).isEmpty()){
            appAreaDAO.add(appArea);
        }
        userDAO.deleteById(user.getId());
    }

    // region <TEST CASE>

    @Test
    public void add_success(){
        appAreaDAO.deleteById(appArea.getId());
        //Test method
        long appAreaId = appAreaDAO.add(appArea);

        Map<String, Object> actualAppArea = getAppAreaFieldsById(appAreaId);
        verifyAddedAppArea(appArea, actualAppArea);
    }

    @Test
    public void deleteById_success(){
        //Test method
        appAreaDAO.deleteById(appArea.getId());

        assertTrue(getAppAreaFieldsById(appArea.getId()).isEmpty());
    }

    @Test
    public void getUsersById_success(){
        long userId = userDAO.add(user);
        userDAO.subscribeToArea(userId, appArea.getId());
        //Test method
        List<User> userList = appAreaDAO.getUsersById(appArea.getId());

        assertTrue(userList.contains(user));
    }

    // endregion

    // region <HELPERS>

    private void verifyAddedAppArea(AppArea appArea, Map<String, Object> actualAppArea) {
        assertEquals(appArea.getName(), actualAppArea.get("Name"));
        assertEquals(appArea.getDescription(), actualAppArea.get("Description"));
        assertEquals(appArea.getTeamName(), actualAppArea.get("TeamName"));
    }

    private Map<String, Object> getAppAreaFieldsById(long id){
        Map<String, Object> fieldsMap = new HashMap<>();
        final String sql = "SELECT * FROM work_flow.apparea " +
                "WHERE id = ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fieldsMap.put("Name",rs.getString(AppAreaDAOImpl.name));
                fieldsMap.put("Description",rs.getString(AppAreaDAOImpl.description));
                fieldsMap.put("TeamName",rs.getString(AppAreaDAOImpl.teamName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fieldsMap;
    }

    // endregion
}
