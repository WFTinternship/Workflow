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

    @Test(expected = RuntimeException.class)
    public void add_nullUser() {
        Post post = DaoTestUtil.getRandomPost();
        post.setUser(null);
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


}
