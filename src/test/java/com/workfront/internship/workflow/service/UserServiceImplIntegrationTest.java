package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static junit.framework.TestCase.assertTrue;


import java.util.List;

/**
 * Created by Vahag on 6/6/2017
 */
public class UserServiceImplIntegrationTest {

    UserService userService;
    User user;

    @Before
    public void setup(){
        userService = new UserServiceImpl();
        user  = DaoTestUtil.getRandomUser();
    }

    @After
    public void tearDown(){
        userService.deleteById(user.getId());
    }

    /**
     * @see UserServiceImpl#add(User)
     */
    @Test
    public void add_success(){
        userService.add(user);

        List<AppArea> usersAppAreas = userService.getAppAreasById(user.getId());
        assertTrue(usersAppAreas.contains(AppArea.REPORTING));
    }

    /**
     * @see UserServiceImpl#add(User)
     */
    @Test(expected = DuplicateEntryException.class)
    public void add_failure(){
        userService.add(user);
        //Test method
        userService.add(user);
    }
}
