package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.PostDAOSpringImpl;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
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

    // region ADD_METHOD

    @Test(expected = RuntimeException.class)
    public void add_nullUser() {
        Post post = DaoTestUtil.getRandomPost();
        post.setUser(null);
        postService.add(post);
        verify(postDAOMock, times(1)).add(post);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullTitle(){
        Post post = DaoTestUtil.getRandomPost();
        post.setTitle(null);
        postService.add(post);
        verify(postDAOMock, times(1)).add(post);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullContent(){
        Post post = DaoTestUtil.getRandomPost();
        post.setContent(null);
        postService.add(post);
        verify(postDAOMock, times(1)).add(post);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullAppArea(){
        Post post = DaoTestUtil.getRandomPost();
        post.setAppArea(null);
        postService.add(post);
        verify(postDAOMock, times(1)).add(post);
    }

    @Test(expected = RuntimeException.class)
    public void add_nullPostTime(){
        Post post = DaoTestUtil.getRandomPost();
        post.setPostTime(null);
        postService.add(post);
        verify(postDAOMock, times(1)).add(post);
    }

    @Test
    public void add_success() {
        Post post = DaoTestUtil.getRandomPost();
        long id = 50;
        when(postDAOMock.add(post)).thenReturn(id);
        long actualId = postService.add(post);
        assertEquals(id, actualId);
        //verify(postDAOMock, times(1)).add(post);
    }

    // endregion

    @Test
    public void setBestAnswer_negativeId(){

    }

}
