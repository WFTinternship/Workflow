package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.PostDAOSpringImpl;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;


/**
 * Created by nane on 6/21/17
 */

public class PostServiceImplUnitTest extends BaseUnitTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostDAOSpringImpl postDAOMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @see PostServiceImpl#add(Post)
     */
    @Test
    public void add_postNotValid() {
        Post post = DaoTestUtil.getRandomPost();
        post.setUser(null);

        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setUser(DaoTestUtil.getRandomUser());
        post.setAppArea(null);
        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setAppArea(AppArea.AGILE);
        post.setPostTime(null);
        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setPostTime(new Timestamp(System.currentTimeMillis()));
        post.setTitle(null);
        try {
            // Test method
            postService.add(post);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        post.setTitle("my title");
        post.setContent(null);
        try {
            // Test method
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
    public void add_success() {
        Post post = DaoTestUtil.getRandomPost();
        long id = 50;
        doReturn(id).when(postDAOMock).add(post);

        // Test method
        long actualId = postService.add(post);
        assertEquals(id, actualId);
    }

    /**
     * @see PostServiceImpl#setBestAnswer(long, long)
     */
    @Test
    public void setBestAnswer_negativeId() {
        try {

            // Test method
            postService.setBestAnswer(-1, 15);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }

        try {

            // Test method
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

        // Test method
        postService.setBestAnswer(id, answerId);
        verify(postDAOMock, times(1)).setBestAnswer(id, answerId);
    }

    /**
     * @see PostServiceImpl#getAll()
     */
    @Test(expected = ServiceLayerException.class)
    public void getAll_DAOException() {
        doThrow(RuntimeException.class).when(postDAOMock).getAll();
        // Test method
        postService.getAll();
    }

    /**
     * @see PostServiceImpl#getAll()
     */
    @Test
    public void getAll_success() {
        // Test method
        postService.getAll();
        verify(postDAOMock, times(1)).getAll();
    }

    /**
     * @see PostServiceImpl#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_NegativeId() {
        // Test method
        postService.getById(-1);
    }

    /**
     * @see PostServiceImpl#getById(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getById_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getById(id);
        // Test method
        postService.getById(id);
    }

    /**
     * @see PostServiceImpl#getById(long)
     */
    @Test
    public void getById_success() {
        Post post = DaoTestUtil.getRandomPost();
        doReturn(post).when(postDAOMock).getById(anyLong());

        // Test method
        Post actualPost = postService.getById(15);
        assertEquals(post, actualPost);
    }

    /**
     * @see PostServiceImpl#getByTitle(String)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByTitle_titleNotValid() {
        // Test method
        postService.getByTitle(null);
    }

    /**
     * @see PostServiceImpl#getByTitle(String)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByTitle_DAOException() {
        String title = "Title";
        doThrow(RuntimeException.class).when(postDAOMock).getByTitle(title);

        // Test method
        postService.getByTitle(title);
    }

    /**
     * @see PostServiceImpl#getByTitle(String)
     */
    @Test
    public void getByTitle_success() {
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByTitle(anyString());

        // Test method
        List<Post> actualPostList = postService.getByTitle("title");
        assertEquals(posts, actualPostList);
    }

    /**
     * @see PostServiceImpl#getByUserId(long)
     */
    @Test
    public void getByUserId_negativeId() {
        try {
            // Test method
            postService.getByUserId(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostServiceImpl#getByUserId(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByUserId_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getByUserId(id);

        // Test method
        postService.getByUserId(id);
    }

    /**
     * @see PostServiceImpl#getByUserId(long)
     */
    @Test
    public void getByUserId_success() {
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByUserId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getByUserId(15);
        assertEquals(posts, actualPosts);
    }

    /**
     * @see PostServiceImpl#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_negativeId() {
        try {
            // Test method
            postService.getByAppAreaId(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostServiceImpl#getByAppAreaId(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByAppAreaId_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getByAppAreaId(id);

        // Test method
        postService.getByAppAreaId(id);
    }

    /**
     * @see PostServiceImpl#getByAppAreaId(long)
     */
    @Test
    public void getByAppAreaId_success() {
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getByAppAreaId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getByAppAreaId(15);
        assertEquals(posts, actualPosts);
    }


    /**
     * @see PostServiceImpl#getAnswersByPostId(long)
     */
    @Test
    public void getAnswersByPostId_negativeId() {
        try {
            // Test method
            postService.getAnswersByPostId(-1);
            fail();
        } catch (Exception ex) {
            assertTrue(ex instanceof InvalidObjectException);
        }
    }

    /**
     * @see PostServiceImpl#getAnswersByPostId(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getAnswersByPostId_DAOException() {
        long id = 15;
        doThrow(RuntimeException.class).when(postDAOMock).getAnswersByPostId(id);

        // Test method
        postService.getAnswersByPostId(id);
    }

    /**
     * @see PostServiceImpl#getAnswersByPostId(long)
     */
    @Test
    public void getAnswersByPostId_success() {
        List<Post> posts = new ArrayList<>();
        doReturn(posts).when(postDAOMock).getAnswersByPostId(anyLong());

        // Test method
        List<Post> actualPosts = postService.getAnswersByPostId(15);
        assertEquals(posts, actualPosts);
    }
}
