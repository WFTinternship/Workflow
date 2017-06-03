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
            if (userDAO.getById(user.getId()) != null) {
                userDAO.deleteById(user.getId());
            }
        }
        if (userDAO.getById(user.getId()) != null) {
            userDAO.deleteById(user.getId());
        }
    }

    // region <TEST CASE>

    /**
     * @see UserDAOImpl#add(User)
     */
    @Test(expected = RuntimeException.class)
    public void add_failure() {
        user.setFirstName(null);
        //Test method
        long userId = userDAO.add(user);
        User actualUser = userDAO.getById(userId);

        assertNull(actualUser);
    }

    /**
     * @see UserDAOImpl#add(User)
     */
    @Test
    public void add_success() {
        long userId = userDAO.add(user);

        User actualUser = userDAO.getById(userId);

        verifyAddedUser(user, actualUser);
    }

    /**
     * @see UserDAOImpl#deleteById(long)
     */
    @Test
    public void deleteById_success() {
        long userId = userDAO.add(user);

        //Test method
        userDAO.deleteById(userId);

        assertNull(userDAO.getById(userId));
    }

    /**
     * @see UserDAOImpl#deleteAll()
     */
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

    /**
     * @see UserDAOImpl#subscribeToArea(long, long)
     */
    @Test(expected = RuntimeException.class)
    public void subscribeToArea_failure(){
        userDAO.add(user);
        //Test method
        userDAO.subscribeToArea(user.getId(), 15);

    }

    /**
     * @see UserDAOImpl#subscribeToArea(long, long)
     */
    @Test
    public void subscribeToArea_success() {
        userDAO.add(user);
        //Test method
        userDAO.subscribeToArea(user.getId(), 1);
        List<User> users = appAreaDAO.getUsersById(1);

        assertTrue(users.contains(user));
    }

    /**
     * @see UserDAOImpl#unsubscribeToArea(long, long)
     */
    @Test
    public void unsubscribeToArea_failure() {
        userDAO.add(user);
        userDAO.subscribeToArea(user.getId(), 1);
        //Test method
        userDAO.unsubscribeToArea(user.getId(), 2);
        List<User> users = appAreaDAO.getUsersById(1);
        assertTrue(users.contains(user));
    }

    /**
     * @see UserDAOImpl#unsubscribeToArea(long, long)
     */
    @Test
    public void unsubscribeToArea_success() {
        userDAO.add(user);
        userDAO.subscribeToArea(user.getId(), 1);
        //Test method
        userDAO.unsubscribeToArea(user.getId(), 1);
        List<User> users = appAreaDAO.getUsersById(1);
        assertTrue(!users.contains(user));
    }

    /**
     * @see UserDAOImpl#getByName(String)
     */
    @Test
    public void getByName_failure() {
        user.setFirstName("Vahagn").setLastName("Vardanyan");
        userDAO.add(user);
        //Test method
        List<User> actualUsers = userDAO.getByName("Vahan");

        assertTrue(!actualUsers.contains(user));
    }

    /**
     * @see UserDAOImpl#getByName(String)
     */
    @Test
    public void getByName_success() {
        user.setFirstName("Vahagn").setLastName("Vardanyan");
        userDAO.add(user);
        //Test method
        List<User> actualUsers = userDAO.getByName("Vahagn Vard");

        assertTrue(actualUsers.contains(user));
    }

    /**
     * @see UserDAOImpl#getById(long)
     */
    @Test
    public void getById_failure() {
        long userId = userDAO.add(user);
        //Test method
        User actualUser = userDAO.getById(1000000);

        assertTrue(!user.equals(actualUser));
    }

    /**
     * @see UserDAOImpl#getById(long)
     */
    @Test
    public void getById_success() {
        long userId = userDAO.add(user);
        //Test method
        User actualUser = userDAO.getById(userId);

        assertTrue(user.equals(actualUser));
    }

    /**
     * @see UserDAOImpl#getAppAreasById(long)
     */
    @Test
    public void getAppAreasById_failure() {
        long userId = userDAO.add(user);
        userDAO.unsubscribeToArea(userId, 1);
        //Test method
        List<AppArea> appAreaList = userDAO.getAppAreasById(userId);

        assertTrue(!appAreaList.contains(AppArea.getById(1)));
    }

    /**
     * @see UserDAOImpl#getAppAreasById(long)
     */
    @Test
    public void getAppAreasById_success() {
        long userId = userDAO.add(user);
        userDAO.subscribeToArea(userId, 1);
        //Test method
        List<AppArea> appAreaList = userDAO.getAppAreasById(userId);

        assertTrue(appAreaList.contains(AppArea.getById(1)));
    }

    // endregion

    // region <HELPERS>

    /**
     * Verifies that the fields of the given users are equal
     * @param user
     * @param actualUser
     */
    static void verifyAddedUser(User user, User actualUser) {
        assertEquals(user.getFirstName(), actualUser.getFirstName());
        assertEquals(user.getLastName(), actualUser.getLastName());
        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getPassword(), actualUser.getPassword());
        assertEquals(user.getRating(), actualUser.getRating());
    }

    /**
     * Verifies that all the users are deleted from the database
     */
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
