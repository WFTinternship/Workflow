package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static junit.framework.TestCase.assertTrue;


import java.util.List;

/**
 * Created by Vahag on 6/6/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config.xml"})
public class UserServiceImplIntegrationTest {

    @Autowired
    private UserService userService;
    private User user;

    @Before
    public void setup(){
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
