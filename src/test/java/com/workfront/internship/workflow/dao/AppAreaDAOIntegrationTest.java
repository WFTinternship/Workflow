package com.workfront.internship.workflow.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertTrue;

public class AppAreaDAOIntegrationTest extends BaseIntegrationTest {

    @Autowired
    @Qualifier("appAreaDAOHibernateImpl")
    private AppAreaDAO appAreaDAO;

    @Autowired
    @Qualifier("userDAOHibernateImpl")
    private UserDAO userDAO;

    private AppArea appArea;
    private User user;

    /**
     * Gets AppArea and creates new User
     */
    @Before
    public void setup() {
        appArea = AppArea.values()[0];
        user = DaoTestUtil.getRandomUser();

        LOG = Logger.getLogger(PostDAOIntegrationTest.class);
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes all the users created during the test
     */
    @After
    public void tearDown() {
        if (appAreaDAO.getById(appArea.getId()) == null) {
            appAreaDAO.add(appArea);
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
     * @see AppAreaDAO#add(AppArea)
     */
    @Test
    public void add_success() {
        appAreaDAO.deleteById(appArea.getId());
        //Test method
        long appAreaId = appAreaDAO.add(appArea);

        AppArea actualAppArea = appAreaDAO.getById(appAreaId);
        assertTrue(appArea.equals(actualAppArea));
    }

    /**
     * @see AppAreaDAO#deleteById(long)
     */
    @Test
    public void deleteById_success() {
        //Test method
        appAreaDAO.deleteById(appArea.getId());

        assertNull(appAreaDAO.getById(appArea.getId()));
    }

    /**
     * @see AppAreaDAO#getUsersById(long)
     */
    @Test
    public void getUsersById_success() {
        long userId = userDAO.add(user);
        userDAO.subscribeToArea(userId, appArea.getId());
        //Test method
        List<User> userList = appAreaDAO.getUsersById(appArea.getId());

        assertTrue(userList.contains(user));
    }

    @Test
    public void getById_success() {
        //Test method
        AppArea actualAppArea = appAreaDAO.getById(1);

        assertTrue(actualAppArea.equals(appArea));
    }

    // endregion
}
