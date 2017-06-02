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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImplIntegrationTest {

    private UserDAO userDAO;
    private AppAreaDAO appAreaDAO;
    private User user;

    List<User> userList;

    @Before
    public void setup() {
        userDAO = new UserDAOImpl();
        appAreaDAO = new AppAreaDAOImpl();
        user = DaoTestUtil.getRandomUser();
        userList = new ArrayList<>();
    }

    @After
    public void tearDown() {
        for (User user : userList) {
            userDAO.deleteById(user.getId());
        }
        userDAO.deleteById(user.getId());
    }

    // region <TEST CASE>

    @Test(expected = RuntimeException.class)
    public void add_failure() {
        user.setFirstName(null);
        long userId = userDAO.add(user);

        User actualUser = userDAO.getById(userId);

        assertNull(actualUser);
    }

    @Test
    public void add_success() {
        long userId = userDAO.add(user);

        User actualUser = userDAO.getById(userId);

        verifyAddedUser(user, actualUser);
    }
        //TODO why doesn't method throw exception
//    @Test(expected = RuntimeException.class)
//    public void deleteById_failure(){
//
//        userDAO.deleteById(0);
//
//    }

    @Test
    public void deleteById_success() {
        long userId = userDAO.add(user);

        //Test method
        userDAO.deleteById(userId);

        assertNull(userDAO.getById(userId));
    }

    @Test
    public void deleteAll_success() {
        userList.add(DaoTestUtil.getRandomUser());
        userList.add(DaoTestUtil.getRandomUser());
        userList.add(DaoTestUtil.getRandomUser());
        for (User user : userList) {
            userDAO.add(user);
        }
        //Test method
        userDAO.deleteAll();

        verifyAllUsersAreDeleted();
    }

    @Test
    public void subscribeToArea_success(){
        userDAO.add(user);
        //Test method
        userDAO.subscribeToArea(user.getId(), 1);
        List<User> users = appAreaDAO.getUsersById(1);

        assertTrue(users.contains(user));
    }

    @Test
    public void unsubscribeToArea_success(){
        userDAO.add(user);
        userDAO.subscribeToArea(user.getId(), 1);
        userDAO.unsubscribeToArea(user.getId(), 1);
        List<User> users = appAreaDAO.getUsersById(1);
        assertEquals(users.size(), 0);
    }

    @Test
    public void getByName_success(){
        user.setFirstName("Vahagn").setLastName("Vardanyan");
        userDAO.add(user);
        //Test method
        List<User> actualUsers = userDAO.getByName("Vahagn Vard");

        assertTrue(actualUsers.contains(user));
    }

    @Test
    public void getById_success(){
        long userId = userDAO.add(user);
        //Test method
        User actualUser = userDAO.getById(userId);

        assertTrue(user.equals(actualUser));
    }

    @Test
    public void getAppAreasById_success(){
        long userId = userDAO.add(user);
        userDAO.subscribeToArea(userId, 1);
        //Test method
        List<AppArea> appAreaList = userDAO.getAppAreasById(userId);

        assertTrue(appAreaList.contains(AppArea.getById(1)));
    }



    // endregion

    // region <HELPERS>

    static void verifyAddedUser(User user, User actualUser) {
        assertEquals(user.getFirstName(), actualUser.getFirstName());
        assertEquals(user.getLastName(), actualUser.getLastName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPassword(), actualUser.getPassword());
        assertEquals(user.getRating(), actualUser.getRating());
    }

    private void verifyAllUsersAreDeleted() {
        final String sql = "SELECT * FROM work_flow.user";
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            assertEquals(rs.last() ? rs.getRow() : 0, 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // endregion

}
