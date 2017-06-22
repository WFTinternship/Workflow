package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.CommentDAOSpringImpl;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.impl.CommentServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;


/**
 * Created by Angel on 6/22/2017
 */
public class CommentServiceImplUnitTest extends  BaseUnitTest{
    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentDAOSpringImpl commentDAOMock;

    @Test
    public void add_commentNotValid() {
       Comment comment = DaoTestUtil.getRandomComment();
       Post post = DaoTestUtil.getRandomPost();
       User user =DaoTestUtil.getRandomUser();
       comment.setUser(null);
       try{
           commentService.add(comment);
           fail();
       }catch(Exception e){
           assertTrue(e instanceof InvalidObjectException);
       }
       comment.setUser(user);
       comment.setPost(null);
       try{
           commentService.add(comment);
           fail();
       }catch(Exception e){
           assertTrue(e instanceof InvalidObjectException);
       }
       comment.setPost(post);
       comment.setContent(null);
       try{
           commentService.add(comment);
           fail();
       }catch(Exception e){
           assertTrue(e instanceof InvalidObjectException);
       }
       comment.setContent("abc");
    }


    @Test
    public void add_success() {
        Comment comment = DaoTestUtil.getRandomComment();
        long id = 7;
        when(commentDAOMock.add(comment)).thenReturn(id);
        long actualId = commentService.add(comment);
        assertEquals(actualId, id);
    }


    @Test(expected = InvalidObjectException.class)
    public void getById_negativeId() {
        Comment comment = DaoTestUtil.getRandomComment();
        long id = -7 ;
        comment.setId(id);
        commentService.getById(id);
    }

    @Test
    public void getById_success() {
        Comment comment = DaoTestUtil.getRandomComment();
        long id = 7;
        when(commentDAOMock.getById(id)).thenReturn(comment);
        Comment actualComment = commentService.getById(id);
        assertEquals(comment,actualComment);
        verify(commentDAOMock, times(1)).getById(id);
    }

    @Test
    public void getAll_nullComments(){

    }

}
