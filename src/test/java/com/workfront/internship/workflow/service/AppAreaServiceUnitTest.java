package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.AppAreaDAOSpringImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.impl.AppAreaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


/**
 * Created by nane on 6/23/17
 */
public class AppAreaServiceUnitTest extends BaseUnitTest {

    @InjectMocks
    AppAreaServiceImpl appAreaService;

    @Mock
    AppAreaDAOSpringImpl appAreaDAOMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @see AppAreaService#add(AppArea)
     */
    @Test(expected = InvalidObjectException.class)
    public void add_nullUser() {
        appAreaService.add(null);
    }

    /**
     * @see AppAreaService#add(AppArea)
     */
    @Test(expected = RuntimeException.class)
    public void add_DAOException() {
        doThrow(RuntimeException.class).when(appAreaDAOMock).add(any(AppArea.class));

        // Test method
        appAreaService.add(AppArea.AGILE);
    }

    /**
     * @see AppAreaService#add(AppArea)
     */
    @Test
    public void add_success() {
        AppArea appArea = AppArea.AGILE;
        long id = 50;
        doReturn(id).when(appAreaDAOMock).add(appArea);

        // Test method
        long actualId = appAreaService.add(appArea);
        assertEquals(id, actualId);

        ArgumentCaptor<AppArea> argument = ArgumentCaptor.forClass(AppArea.class);
        verify(appAreaDAOMock, only()).add(argument.capture());
        assertEquals(appArea, argument.getValue());
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getUsersById_negativeId() {
        appAreaService.getUsersById(-1);
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Test(expected = RuntimeException.class)
    public void getUsersById_DAOException() {
        doThrow(RuntimeException.class).when(appAreaDAOMock).getUsersById(anyLong());

        // Test method
        appAreaService.getUsersById(15);
    }

    /**
     * @see AppAreaService#getUsersById(long)
     */
    @Test
    public void getUsersById_success() {
        Long id = 1L;
        List<User> users = new ArrayList<>();
        doReturn(users).when(appAreaDAOMock).getUsersById(id);

        // Test method
        List<User> actualUsers = appAreaService.getUsersById(id);
        assertEquals(users, actualUsers);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(appAreaDAOMock, only()).getUsersById(argument.capture());
        assertEquals(id, argument.getValue());
    }

    /**
     * @see AppAreaService#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_negativeId() {
        appAreaService.getById(-1);
    }

    /**
     * @see AppAreaService#getById(long)
     */
    @Test(expected = RuntimeException.class)
    public void getById_DAOException() {
        doThrow(RuntimeException.class).when(appAreaDAOMock).getById(anyLong());

        // Test method
        appAreaService.getById(15);
    }

    /**
     * @see AppAreaService#getById(long)
     */
    @Test
    public void getById_success() {
        Long id = 15L;
        doReturn(AppArea.AGILE).when(appAreaDAOMock).getById(id);

        // Test method
        AppArea actualAppArea = appAreaService.getById(id);
        assertEquals(AppArea.AGILE, actualAppArea);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(appAreaDAOMock, only()).getById(argument.capture());
        assertEquals(id, argument.getValue());
    }

    /**
     * @see AppAreaService#deleteById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void deleteById_negativeId() {
        appAreaService.deleteById(-1);
    }

    /**
     * @see AppAreaService#deleteById(long)
     */
    @Test(expected = RuntimeException.class)
    public void deleteById_DAOException() {
        doThrow(RuntimeException.class).when(appAreaDAOMock).deleteById(anyLong());

        // Test method
        appAreaService.deleteById(15);
    }

    /**
     * @see AppAreaService#deleteById(long)
     */
    @Test
    public void deleteById_success() {
        Long id = 5L;

        // Test method
        appAreaService.deleteById(id);
        verify(appAreaDAOMock, times(1)).deleteById(id);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(appAreaDAOMock, only()).deleteById(argument.capture());
        assertEquals(id, argument.getValue());
    }
}
