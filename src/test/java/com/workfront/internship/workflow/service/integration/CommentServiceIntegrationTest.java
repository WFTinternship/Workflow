package com.workfront.internship.workflow.service.integration;

import com.workfront.internship.workflow.dao.CommentDAOIntegrationTest;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Angel on 6/22/2017
 */
public class CommentServiceIntegrationTest extends BaseIntegrationTest{

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    private Comment comment;
    private Post post;
    private User user;

    @Before
    public void setup() {
        post = DaoTestUtil.getRandomPost();
        user = DaoTestUtil.getRandomUser();
        comment = DaoTestUtil.getRandomComment(user,post);
    }
    @After
    public void tearDown() {
        if(comment.getId() > 0 && commentService.getById(comment.getId()) != null){
            commentService.delete(comment.getId());
        }
    }

    /**
     * @see CommentService#add(Comment)
     */
    @Test
    public void add_success() {

        post.setUser(comment.getUser());

        userService.add(comment.getUser());
        postService.add(comment.getPost());

        //Test method
        commentService.add(comment);

        Comment actualComment = commentService.getById(comment.getId());
        CommentDAOIntegrationTest.isCommentsEqual(comment,actualComment,false);

        postService.delete(comment.getPost().getId());
        userService.deleteById(comment.getUser().getId());
    }


    /**
     * @see CommentService#add(Comment)
     */
    @Test(expected = InvalidObjectException.class)
    public void add_failure() {
        commentService.add(null);
    }


    /**
     * @see CommentService#getById(long)
     */
    @Test
    public void getById_success() {

        post.setUser(user);

        userService.add(comment.getUser());
        postService.add(comment.getPost());
        commentService.add(comment);

        //Test method
        Comment expectedComment = commentService.getById(comment.getId());
        CommentDAOIntegrationTest.isCommentsEqual(comment,expectedComment,false);

        postService.delete(comment.getPost().getId());
        userService.deleteById(comment.getUser().getId());


    }
    /**
     * @see CommentService#getById(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getById_failure() {
        commentService.getById(-7);
    }


    /**
     * @see CommentService#getByPostId(long)
     */
    @Test
    public void getByPostId_success() {

        post.setUser(user);

        userService.add(comment.getUser());
        postService.add(comment.getPost());
        commentService.add(comment);

        //Test method
        List<Comment> comments = commentService.getByPostId(comment.getPost().getId());
        assertNotNull(comments);

        assertTrue(comments.contains(comment));

        postService.delete(comment.getPost().getId());
        userService.deleteById(comment.getUser().getId());

    }


    /**
     * @see CommentService#getByPostId(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void getByPostId_failure() {
        commentService.getByPostId(-7);
    }


    /**
     * @see CommentService#getAll()
     */
    @Test
    public void getAll_success() {

        post.setUser(user);

        userService.add(comment.getUser());
        postService.add(comment.getPost());

        List<Comment> comments = commentService.getAll();
        int size = comments.size();

        commentService.add(comment);

        //Test method
        comments = commentService.getAll();

        assertTrue(comments.size()==size+1 && comments.contains(comment));

        postService.delete(comment.getPost().getId());
        userService.deleteById(comment.getUser().getId());

    }


    /**
     * @see CommentService#update(long, String)
     */
    @Test
    public void update_success() {

        post.setUser(user);

        userService.add(comment.getUser());
        postService.add(comment.getPost());
        commentService.add(comment);

        comment.setContent("abc");

        //Test method
        commentService.update(comment.getId(),"abc");

        Comment updatedComment = commentService.getById(comment.getId());
        assertEquals(comment,updatedComment);

        postService.delete(comment.getPost().getId());
        userService.deleteById(comment.getUser().getId());
    }

    /**
     * @see CommentService#update(long, String)
     */
    @Test
    public void update_failure() {
       try{
           commentService.update(-7,"abc");
       }catch(Exception e){
           assertTrue(e instanceof InvalidObjectException);
       }
       try{
           commentService.update(7,null);
       }catch(Exception e ){
           assertTrue(e instanceof InvalidObjectException);
       }

    }

    /**
     * @see CommentService#delete(long)
     */
    @Test
    public void delete_success() {

        post.setUser(user);

        userService.add(comment.getUser());
        postService.add(comment.getPost());
        commentService.add(comment);

        //Test method
        commentService.delete(comment.getId());
        assertNull(commentService.getById(comment.getId()));

        postService.delete(comment.getPost().getId());
        userService.deleteById(comment.getUser().getId());

    }

    /**
     * @see CommentService#delete(long)
     */
    @Test(expected = InvalidObjectException.class)
    public void delete_failure() {
        commentService.delete(-7);
    }
}
