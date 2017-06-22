package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.DuplicateEntryException;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.impl.UserServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Vahag on 6/22/2017
 */
public class UserServiceImplUnitTest extends BaseUnitTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDAOSpringImpl userDAOMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

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

    @Test(expected = DuplicateEntryException.class)
    public void add_existingUser() {
        User user = DaoTestUtil.getRandomUser();
        when(userDAOMock.getByEmail(anyString())).thenReturn(user);
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

    @Test(expected = InvalidObjectException.class)
    public void getByName_emptyName() {
        userService.getByName(null);
    }

    @Test(expected = ServiceLayerException.class)
    public void getByName_DAOException() {
        when(userDAOMock.getByName(anyString())).thenThrow(RuntimeException.class);
        userService.getByName("123");
    }

    @Test
    public void getByName_success() {
        List<User> userList = new ArrayList<>();
        when(userDAOMock.getByName(anyString())).thenReturn(userList);
        List<User> actualList = userService.getByName("abc");
        assertEquals(actualList, userList);
    }

    @Test(expected = InvalidObjectException.class)
    public void getById_negativeId() {
        userService.getById(-1);
    }

    @Test(expected = ServiceLayerException.class)
    public void getById_DAOException() {
        when(userDAOMock.getByName(anyString())).thenThrow(RuntimeException.class);
        userService.getByName("123");
    }

    @Test
    public void getById_success() {
        List<User> userList = new ArrayList<>();
        when(userDAOMock.getByName(anyString())).thenReturn(userList);
        List<User> actualList = userService.getByName("abc");
        assertEquals(actualList, userList);
    }


}
