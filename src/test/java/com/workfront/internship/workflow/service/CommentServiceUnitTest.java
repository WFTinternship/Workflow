package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.dao.springJDBC.CommentDAOSpringImpl;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.impl.CommentServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.*;


/**
 * Created by Angel on 6/22/2017
 */
public class CommentServiceUnitTest extends  BaseUnitTest {

    @InjectMocks
    CommentServiceImpl commentService;

    @Mock
    CommentDAOSpringImpl commentDAOMock;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    /**
     * @see CommentService#add(Comment)
     */
    @Test
    public void add_commentNotValid() {
       Comment comment = DaoTestUtil.getRandomComment();

       comment.setUser(null);
       try {
           //Test method
           commentService.add(comment);
           fail();
       } catch(Exception e) {
           assertTrue(e instanceof InvalidObjectException);
       }
       comment.setUser(DaoTestUtil.getRandomUser());

       comment.setPost(null);
       try {
           //Test method
           commentService.add(comment);
           fail();
       } catch(Exception e) {
           assertTrue(e instanceof InvalidObjectException);
       }
       comment.setPost(DaoTestUtil.getRandomPost());

       comment.setContent(null);
       try {
           //Test method
           commentService.add(comment);
           fail();
       } catch(Exception e) {
           assertTrue(e instanceof InvalidObjectException);
       }
    }

    /**
     * @see CommentService#add(Comment)
     */
    @Test
    public void add_success() {

        Comment comment = DaoTestUtil.getRandomComment();

        long id = 7;
        doReturn(id).when(commentDAOMock).add(comment);

        //Test method
        long actualId = commentService.add(comment);
        assertEquals(actualId, id);

        ArgumentCaptor<Comment> argument = ArgumentCaptor.forClass(Comment.class);

        verify(commentDAOMock, times(1)).add(argument.capture());
        verifyNoMoreInteractions(commentDAOMock);

        assertEquals(argument.getValue(),comment);
    }

    /**
     * @see CommentService#add(Comment)
     */
    @Test
    public void add_failure() {
        Comment comment = DaoTestUtil.getRandomComment();
        long id = 50;
        doReturn(id).when(commentDAOMock).add(comment);

        // Test method
        long actualId = commentService.add(comment);
        assertEquals(id, actualId);
    }

    /**
     * @see CommentService#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_negativeId() {
        //Test method
        commentService.getById(-7);
    }

    /**
     * @see CommentService#getById(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getById_DAOException() {
        Long id = 7L;

        doThrow(RuntimeException.class).when(commentDAOMock).getById(id);

        //Test method
        commentService.getById(id);
    }

    /**
     * @see CommentService#getById(long)
     */
    @Test
    public void getById_success() {
        Comment comment = DaoTestUtil.getRandomComment();

        Long id = 7L;
        doReturn(comment).when(commentDAOMock).getById(id);

        //Test method
        Comment actualComment = commentService.getById(id);
        assertEquals(comment,actualComment);

        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        verify(commentDAOMock,times(1)).getById(argument.capture());
        verifyNoMoreInteractions(commentDAOMock);

        assertEquals(argument.getValue(),id);
    }

    /**
     * @see CommentService#getAll()
     */
    @Test(expected = ServiceLayerException.class)
    public void getAll_DAOException() {
        doThrow(RuntimeException.class).when(commentDAOMock).getAll();

        //Test method
        commentService.getAll();
    }

    /**
     * @see CommentService#getAll()
     */
    @Test
    public void getAll_success() {
        List<Comment> commentsList = new ArrayList<>();
        doReturn(commentsList).when(commentDAOMock).getAll();

        //Test method
        List<Comment> actualCommentsList = commentService.getAll();
        assertEquals(commentsList,actualCommentsList );


    }

    /**
     * @see CommentService#getByPostId(long)
     */
    @Test
    public void getByPostId_success() {
        List<Comment> commentsList = new ArrayList<>();
        doReturn(commentsList).when(commentDAOMock).getByPostId(anyLong());

        //Test method
        List<Comment> actualCommentsList = commentService.getByPostId(7);
        assertEquals(commentsList,actualCommentsList );
    }

    /**
     * @see CommentService#getByPostId(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByPostId_negativeId() {
        commentService.getById(-7);
    }

    /**
     * @see CommentService#getByPostId(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void getByPostId_DAOException() {
        long id = 7;

        doThrow(RuntimeException.class).when(commentDAOMock).getByPostId(id);

        //Test method
        commentService.getByPostId(id);
    }

    /**
     * @see CommentService#update(long, String)
     */
    @Test
    public void update_CommentNotValid(){
        try{
            commentService.update(-7,"abc");
        }catch(Exception e){
            assertTrue(e instanceof InvalidObjectException);
        }
        try{
            commentService.update(7,null);
        }catch(Exception e){
            assertTrue(e instanceof InvalidObjectException);
        }
    }

    /**
     * @see CommentService#update(long, String)
     */
    @Test(expected = ServiceLayerException.class)
    public void update_DAOException(){
        Long id = 7L;
        String newContent = "new content";

        doThrow(RuntimeException.class).when(commentDAOMock).update(id,newContent);

        //Test method
        commentService.update(id,newContent);
    }

    /**
     * @see CommentService#update(long, String)
     */
    @Test
    public void update_success(){

        Long id = 7L;
        String newContent = "new content";

        //Test method
        commentService.update(id,newContent);

        verify(commentDAOMock, times(1)).update(id, newContent);

    }

    /**
     * @see CommentService#delete(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void delete_negativeId(){

        Long id = -7L;

        //Test method
        commentService.delete(id);
    }

    /**
     * @see CommentService#delete(long)
     */
    @Test(expected = ServiceLayerException.class)
    public void delete_DAOException(){

        Long id = -7L;

        doThrow(RuntimeException.class).when(commentDAOMock).delete(anyInt());

        //Test method
        commentService.delete(id);

    }

    /**
     * @see CommentService#delete(long)
     */
    @Test
    public void delete_success(){

        Long id = 7L;

        //Test method
        commentService.delete(id);

        verify(commentDAOMock,times(1)).delete(anyInt());

    }
}
