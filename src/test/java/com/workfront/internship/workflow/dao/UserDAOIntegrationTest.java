package com.workfront.internship.workflow.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOIntegrationTest extends BaseIntegrationTest{

    @Autowired
    @Qualifier("userDAOSpringImpl")
    private UserDAO userDAO;

    @Autowired
    @Qualifier("appAreaDAOSpringImpl")
    private AppAreaDAO appAreaDAO;

    @Autowired
    @Qualifier("postDAOSpringImpl")
    private PostDAO postDAO;

    private User user;
    private List<User> userList;
    private Post post;
    private AppArea appArea;

    @Before
    public void setup() {
        user = DaoTestUtil.getRandomUser();
        userList = new ArrayList<>();
        appArea = AppArea.values()[0];

        LOG = Logger.getLogger(UserDAOIntegrationTest.class);
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void tearDown() throws SQLException {
        for (User user : userList) {
            if (userDAO.getById(user.getId()) != null) {
                userDAO.deleteById(user.getId());
            }
        }

        userDAO.deleteById(user.getId());
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // region <TEST CASE>

    /**
     * @see UserDAO#add(User)
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
     * @see UserDAO#add(User)
     */
    @Test
    public void add_success() {
        long userId = userDAO.add(user);

        User actualUser = userDAO.getById(userId);

        verifyAddedUser(user, actualUser);
    }

    /**
     * @see UserDAO#deleteById(long)
     */
    @Test
    public void deleteById_success() {
        long userId = userDAO.add(user);

        //Test method
        userDAO.deleteById(userId);

        assertNull(userDAO.getById(userId));
    }

    /**
     * @see UserDAO#deleteAll()
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
     * @see UserDAO#subscribeToArea(long, long)
     */
    @Test(expected = RuntimeException.class)
    public void subscribeToArea_failure(){
        userDAO.add(user);
        //Test method
        userDAO.subscribeToArea(user.getId(), 10000);

    }

    /**
     * @see UserDAO#subscribeToArea(long, long)
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
     * @see UserDAO#unsubscribeToArea(long, long)
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
     * @see UserDAO#unsubscribeToArea(long, long)
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
     * @see UserDAO#getByName(String)
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
     * @see UserDAO#getByName(String)
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
     * @see UserDAO#getByEmail(String)
     */
    @Test
    public void getByEmail_failure() {
        //Test method
        User actualUser = userDAO.getByEmail("123");
        assertNull(actualUser);
    }

    /**
     * @see UserDAO#getByEmail(String)
     */
    @Test
    public void getByEmail_success() {
        user.setEmail("vahagn@gmail.com");
        userDAO.add(user);
        //Test method
        User actualUser = userDAO.getByEmail("vahagn@gmail.com");

        assertTrue(actualUser.equals(user));
    }

    /**
     * @see UserDAO#getById(long)
     */
    @Test
    public void getById_failure() {
        long userId = userDAO.add(user);
        //Test method
        User actualUser = userDAO.getById(1000000);

        assertTrue(!user.equals(actualUser));
    }

    /**
     * @see UserDAO#getById(long)
     */
    @Test
    public void getById_success() {
        long userId = userDAO.add(user);
        //Test method
        User actualUser = userDAO.getById(userId);

        assertTrue(user.equals(actualUser));
    }

    /**
     * @see UserDAO#getAppAreasById(long)
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
     * @see UserDAO#getAppAreasById(long)
     */
    @Test
    public void getAppAreasById_success() {
        long userId = userDAO.add(user);
        userDAO.subscribeToArea(userId, 1);
        //Test method
        List<AppArea> appAreaList = userDAO.getAppAreasById(userId);

        assertTrue(appAreaList.contains(AppArea.getById(1)));
    }

    /**
     * @see UserDAO#getLikedPosts(long)
     */
    @Test
    public void getLikedPosts_failure() {
        long userId = userDAO.add(user);
        post = DaoTestUtil.getRandomPost(user, appArea);
        long postId = postDAO.add(post);

        postDAO.like(userId, postId);

        //Test method
        List<Post> likedPostsList = userDAO.getLikedPosts(userId + 1);

        assertTrue(!likedPostsList.contains(post));
    }

    /**
     * @see UserDAO#getLikedPosts(long)
     */
    @Test
    public void getLikedPosts_success() {
        long userId = userDAO.add(user);
        post = DaoTestUtil.getRandomPost(user, appArea);
        long postId = postDAO.add(post);

        postDAO.like(userId, postId);

        //Test method
        List<Post> likedPostsList = userDAO.getLikedPosts(userId);

        assertTrue(likedPostsList.contains(post));
    }

    /**
     * @see UserDAO#getDislikedPosts(long)
     */
    @Test
    public void getDislikedPosts_failure() {
        long userId = userDAO.add(user);
        post = DaoTestUtil.getRandomPost(user, appArea);
        long postId = postDAO.add(post);

        postDAO.dislike(userId, postId);

        //Test method
        List<Post> dislikedPostsList = userDAO.getDislikedPosts(userId + 1);

        assertTrue(!dislikedPostsList.contains(post));
    }

    /**
     * @see UserDAO#getDislikedPosts(long)
     */
    @Test
    public void getDislikedPosts_success() {
        long userId = userDAO.add(user);
        post = DaoTestUtil.getRandomPost(user, appArea);
        long postId = postDAO.add(post);

        postDAO.dislike(userId, postId);

        //Test method
        List<Post> dislikedPostsList = userDAO.getDislikedPosts(userId);

        assertTrue(dislikedPostsList.contains(post));
    }

    /**
     * @see UserDAO#updateProfile(User)
     */
    @Test(expected = RuntimeException.class)
    public void update_failure() {
        userDAO.add(user);
        User updatedUser = user.setFirstName(null);
        userDAO.updateProfile(updatedUser);
    }

    /**
     * @see UserDAO#updateProfile(User)
     */
    @Test
    public void update_success() {
        userDAO.add(user);
        user.setLastName("new Last Name");
        userDAO.updateProfile(user);
        User updatedUser = userDAO.getById(user.getId());
        verifyAddedUser(user, updatedUser);
    }

    /**
     * @see UserDAO#updateRating(User)
     */
    @Test
    public void updateRating_success() {
        userDAO.add(user);
        user.setRating(10);
        userDAO.updateRating(user);
        User updatedUser = userDAO.getById(user.getId());
        verifyAddedUser(user, updatedUser);
    }
    // endregion

    // region <HELPERS>

    /**
     * Verifies that the fields of the given users are equal
     * @param user
     * @param actualUser
     */
    public static void verifyAddedUser(User user, User actualUser) {
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
        final String sql = "SELECT * FROM user";
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            assertEquals(rs.last() ? rs.getRow() : 0, 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // endregion

}
