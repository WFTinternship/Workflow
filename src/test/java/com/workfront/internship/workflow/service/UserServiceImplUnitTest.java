package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Vahag on 6/22/2017
 */
public class UserServiceImplUnitTest extends BaseUnitTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDAOSpringImpl userDAOMock;

    @Test(expected = InvalidObjectException.class)
    public void add_nullUser() {
        userService.add(null);
    }

    @Test(expected = InvalidObjectException.class)
    public void add_nullFirstName() {
        User user = DaoTestUtil.getRandomUser();
        user.setFirstName(null);
        userService.add(user);
    }

    @Test(expected = InvalidObjectException.class)
    public void add_nullLastName() {
        User user = DaoTestUtil.getRandomUser();
        user.setLastName(null);
        userService.add(user);
    }

    @Test(expected = InvalidObjectException.class)
    public void add_nullEmail() {
        User user = DaoTestUtil.getRandomUser();
        user.setEmail(null);
        userService.add(user);
    }

    @Test
    public void add_success() {
        User user = DaoTestUtil.getRandomUser();
        long id = 15;
        when(userDAOMock.add(user)).thenReturn(id);
        long actualId = userService.add(user);
        assertEquals(actualId, id);
    }
}
