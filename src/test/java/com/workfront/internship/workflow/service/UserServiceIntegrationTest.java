package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.util.DaoTestUtil;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vahag on 6/6/2017
 */

public class UserServiceIntegrationTest extends BaseIntegrationTest{

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void setup(){
        user  = DaoTestUtil.getRandomUser();
    }

    @After
    public void tearDown(){
        if (user.getId() > 0 && userService.getById(user.getId()) != null) {
            userService.deleteById(user.getId());
        }
    }

    // region <TEST_CASE>

    /**
     * @see UserService#add(User)
     */
    @Test
    public void add_success(){
        //Test method
        userService.add(user);

        User actualUser = userService.getById(user.getId());
        assertEquals(user, actualUser);
        List<AppArea> usersAppAreas = userService.getAppAreasById(user.getId());
        assertTrue(usersAppAreas.containsAll(Arrays.asList(AppArea.values())));
    }

    /**
     * @see UserService#add(User)
     */
    @Test(expected = DuplicateEntryException.class)
    public void add_failure(){
        userService.add(user);

        //Test method
        userService.add(user);
    }

    /**
     * @see UserService#getByName(String)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByName_failure(){
        //Test method
        userService.getByName("");
    }

    /**
     * @see UserService#getByName(String)
     */
    @Test
    public void getByName_success(){
        userService.add(user);

        //Test method
        List<User> actualUsers = userService.getByName(user.getFirstName());
        assertTrue(actualUsers.contains(user));
    }

    /**
     * @see UserService#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_failure(){
        //Test method
        userService.getById(-1);
    }

    /**
     * @see UserService#getById(long)
     */
    @Test
    public void getById_success(){
        userService.add(user);

        //Test method
        User actualUser = userService.getById(user.getId());
        assertEquals(user, actualUser);
    }

    /**
     * @see UserService#getByEmail(String)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByEmail_failure(){
        //Test method
        userService.getByEmail("");
    }

    /**
     * @see UserService#getByEmail(String)
     */
    @Test
    public void getByEmail_success(){
        userService.add(user);

        //Test method
        User actualUser = userService.getByEmail(user.getEmail());
        assertEquals(user, actualUser);
    }

    /**
     * @see UserService#getAppAreasById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getAppAreasById_failure(){
        //Test method
        userService.getAppAreasById(-1);
    }

    /**
     * @see UserService#getAppAreasById(long)
     */
    @Test
    public void getAppAreasById_success(){
        userService.add(user);

        //Test method
        List<AppArea> actualAppAreas = userService.getAppAreasById(user.getId());
        assertTrue(actualAppAreas.containsAll(Arrays.asList(AppArea.values())));
    }

    /**
     * @see UserService#subscribeToArea(long, long)
     */
    @Test(expected = InvalidObjectException.class)
    public void subscribeToArea_failure(){
        //Test method
        userService.subscribeToArea(0, 1);
    }

    /**
     * @see UserService#subscribeToArea(long, long)
     */
    @Test
    public void subscribeToArea_success(){
        userService.add(user);
        userService.unsubscribeToArea(user.getId(), 1);

        //Test method
        userService.subscribeToArea(user.getId(), 1);
        List<AppArea> actualAppAreas = userService.getAppAreasById(user.getId());
        assertTrue(actualAppAreas.contains(AppArea.getById(1)));
    }

    /**
     * @see UserService#unsubscribeToArea(long, long)
     */
    @Test(expected = InvalidObjectException.class)
    public void unsubscribeToArea_failure(){
        //Test method
        userService.unsubscribeToArea(1, 0);
    }

    /**
     * @see UserService#unsubscribeToArea(long, long)
     */
    @Test
    public void unsubscribeToArea_success(){
        userService.add(user);

        //Test method
        userService.unsubscribeToArea(user.getId(), 1);
        List<AppArea> actualAppAreas = userService.getAppAreasById(user.getId());
        assertTrue(!actualAppAreas.contains(AppArea.getById(1)));
    }

    /**
     * @see UserService#updateProfile(User)
     */
    @Test(expected = InvalidObjectException.class)
    public void update_failure() {
        userService.updateProfile(null);
    }

    /**
     * @see PostService#update(Post)
     */
    @Test
    public void update_success() {
        userService.add(user);

        user.setFirstName("some name");
        // Test Method
        userService.updateProfile(user);
        User expectedUser = userService.getById(user.getId());

        TestCase.assertEquals(user.getFirstName(), expectedUser.getFirstName());

        userService.deleteById(user.getId());
    }

    /**
     * @see UserService#deleteById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void deleteById_failure() {
        //Test method
        userService.deleteById(0);
    }

    /**
     * @see UserService#deleteById(long)
     */
    @Test
    public void deleteById_success() {
        long userId = userService.add(user);

        //Test method
        userService.deleteById(userId);
        assertEquals(userService.getById(userId), null);
    }

    /**
     * @see UserService#deleteAll()
     */
    @Test
    public void deleteAll_success() {
        userService.add(user);

        //Test method
        userService.deleteAll();
        assertEquals(userService.getById(user.getId()), null);
    }

    // endregion

}
