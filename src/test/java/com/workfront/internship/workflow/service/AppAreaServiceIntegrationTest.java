package com.workfront.internship.workflow.service;


import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Vahag on 6/24/2017
 */
public class AppAreaServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private AppAreaService appAreaService;

    @Autowired
    private UserService userService;

    private AppArea appArea;
    private User user;

    @Before
    public void setup() {
        appArea = AppArea.values()[0];
        user = DaoTestUtil.getRandomUser();
    }

    /**
     * Deletes all the users created during the test and adds the appArea that might be deleted during tests
     */
    @After
    public void tearDown() {
        if (appAreaService.getById(appArea.getId()) == null) {
            appAreaService.add(appArea);
        }

        if (user.getId() > 0) {
            userService.deleteById(user.getId());
        }
    }

    // region <TEST_CASE>

    /**
     * @see AppAreaService#add(AppArea)
     */
    @Test
    public void add_success() {
        appAreaService.deleteById(appArea.getId());
        //Test method
        long appAreaId = appAreaService.add(appArea);

        AppArea actualAppArea = appAreaService.getById(appAreaId);
        assertTrue(appArea.equals(actualAppArea));
    }

    /**
     * @see AppAreaService#add(AppArea)
     */
    @Test(expected = InvalidObjectException.class)
    public void add_failure() {
        //Test method
        appAreaService.add(null);
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Test
    public void getUsersById_success() {
        userService.add(user);
        //Test method
        List<User> userList = appAreaService.getUsersById(appArea.getId());

        assertTrue(userList.contains(user));
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getUsersById_failure() {
        //Test method
        appAreaService.getUsersById(0);
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Test
    public void getById_success() {
        //Test method
        AppArea actualAppArea = appAreaService.getById(appArea.getId());

        assertEquals(actualAppArea, appArea);
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_failure() {
        //Test method
        appAreaService.getUsersById(0);
    }

    /**
     * @see AppAreaService#deleteById(long)
     */
    @Test
    public void deleteById_success() {
        //Test method
        appAreaService.deleteById(appArea.getId());

        assertNull(appAreaService.getById(appArea.getId()));
    }

    /**
     * @see AppAreaService#deleteById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void deleteById_failure() {
        //Test method
        appAreaService.deleteById(0);
    }

    // endregion

}
