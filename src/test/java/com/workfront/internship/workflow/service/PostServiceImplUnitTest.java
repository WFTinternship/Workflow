package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.PostDAOSpringImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;


/**
 * Created by nane on 6/21/17
 */

public class PostServiceImplUnitTest extends BaseUnitTest {

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PostDAOSpringImpl postDAOMock;

    /**
     * @see PostServiceImpl#add(Post)
     */
    @Test
    public void add_postNotValid() {
        Post post = DaoTestUtil.getRandomPost();
        post.setUser(null);

        try {
            // test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setUser(DaoTestUtil.getRandomUser());
        post.setAppArea(null);
        try {
            // test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setAppArea(AppArea.AGILE);
        post.setPostTime(null);
        try {
            // test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setPostTime(new Timestamp(System.currentTimeMillis()));
        post.setTitle(null);
        try {
            // test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setTitle("my title");
        post.setContent(null);
        try {
            // test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostServiceImpl#add(Post)
     */
    @Test
    public void add_failure() {
        Post post = DaoTestUtil.getRandomPost();
        long id = 50;
        doReturn(id).when(postDAOMock).add(post);

        // Test method
        long actualId = postService.add(post);
        assertEquals(id, actualId);
    }

    /**
     * @see PostServiceImpl#add(Post)
     */
    @Test
    public void add_success() {
        Post post = DaoTestUtil.getRandomPost();
        long id = 50;
        when(postDAOMock.add(post)).thenReturn(id);
        long actualId = postService.add(post);
        assertEquals(id, actualId);
    }


    /**
     * @see PostServiceImpl#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_negativeId() {
        try {
            postService.setBestAnswer(-1, 15);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        try {
            postService.setBestAnswer(15, -1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostServiceImpl#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_success() {
        long id = 17, answerId = 15;
        postService.setBestAnswer(id, answerId);
        verify(postDAOMock, times(1)).setBestAnswer(id, answerId);
    }

    /**
     * @see PostServiceImpl#getAll()
     */
    @Test(expected = ServiceLayerException.class)
    public void getAll_DAOException() {
        when(postDAOMock.getAll()).thenThrow(RuntimeException.class);
        postService.getAll();
    }

    /**
     * @see PostServiceImpl#getAll()
     */
    @Test
    public void getAll_success() {
        postService.getAll();
        verify(postDAOMock, times(1)).getAll();
    }

    /**
     * @see PostServiceImpl#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_NegativeId() {
        postService.getById(-1);
    }

    @Test(expected = ServiceLayerException.class)
    public void getById_DAOException() {
        long id = 15;
        when(postDAOMock.getById(id)).thenThrow(RuntimeException.class);
        postService.getById(id);
    }

    @Test
    public void getById_success() {
        Post post = DaoTestUtil.getRandomPost();
        long id = 15;
        when(postDAOMock.getById(id)).thenReturn(post);
        Post actualPost = postService.getById(id);
        assertEquals(post, actualPost);
    }


    /**
     * @see PostServiceImpl#getByTitle(String)
     */

    @Test(expected = InvalidObjectException.class)
    public void getByTitle_NullTitle() {
        postService.getByTitle(null);
    }


}
