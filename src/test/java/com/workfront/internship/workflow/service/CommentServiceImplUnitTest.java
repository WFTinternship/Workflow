package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.CommentDAOSpringImpl;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.service.impl.CommentServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;


/**
 * Created by Angel on 6/22/2017
 */
public class CommentServiceImplUnitTest extends  BaseUnitTest{
    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentDAOSpringImpl commentDAOMock;

    @Test(expected = RuntimeException.class)
    public void add_nullComment() {
        Comment comment = DaoTestUtil.getRandomComment();
        comment.setUser(null);
        comment.setPost(null);
        commentService.add(comment);
        verify(commentDAOMock,times(1)).add(comment);
    }

}
