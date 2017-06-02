package com.workfront.internship.dao;

import com.workfront.internship.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.dao.impl.CommentDAOImpl;
import com.workfront.internship.dao.impl.PostDAOImpl;
import com.workfront.internship.dao.impl.UserDAOImpl;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.Comment;
import com.workfront.internship.dataModel.Post;
import com.workfront.internship.dataModel.User;
import com.workfront.internship.util.DaoTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;


/**
 * Created by angel on 30.05.2017
 */
public class CommentDAOImplIntegrationTest {
    //private CommentDAO commentDAO;
    private Comment comment;
    private CommentDAO commentDAO;

    private User user;
    private UserDAO userDAO;

    private Post post;
    private PostDAO postDAO;

    private AppArea appArea;
    private AppAreaDAO appAreaDAO;

    List<Comment> commentList = new ArrayList<>();

    @Before
    public void setup(){
       // comment = new Comment();
        userDAO = new UserDAOImpl();
        user=  DaoTestUtil.getRandomUser();
        userDAO.add(user);

        appAreaDAO = new AppAreaDAOImpl();
        appArea = DaoTestUtil.getRandomAppArea();
//        appAreaDAO.add(appArea);

        postDAO = new PostDAOImpl();
        post=  DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(post);

        commentDAO = new CommentDAOImpl();
        comment = DaoTestUtil.getRandomComment(user, post);
        commentDAO.add(comment);
    }

    @After
    public void tearDown(){

    }

    @Test
    public void add_success(){
        long expectedCommentId = commentDAO.add(comment);
        Comment actualComment = commentDAO.getById(expectedCommentId);
        isCommentsEqual(comment, actualComment);
    }

    @Test(expected = RuntimeException.class)
    public void add_failure(){
        comment.setPost(null);
        long commentId = commentDAO.add(comment);
        Comment comment = commentDAO.getById(commentId);
        assertNull(comment);
    }

    @Test
    public void update_success(){
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);

        Long id = commentDAO.add(otherComment);
        assertNotNull(id);
        assertTrue(id > 0);

        String newComment = "some new comment";
        otherComment.setContent(newComment);

        // Test method
        boolean updated = commentDAO.update(otherComment.getId(), otherComment.getContent());
        assertTrue(updated);

        // acquire stored/updated comment
        Comment updatedComment = commentDAO.getById(otherComment.getId());

        isCommentsEqual(otherComment, updatedComment, true);
    }

    @Test
    public void update_failure(){

    }

    @Test
    public void delete_success(){

    }

    @Test
    public void delete_failure(){

    }

    @Test
    public void getById_success(){

    }
    @Test
    public void getById_failure(){

    }
    @Test
    public void getAll_success(){

    }

    private void isCommentsEqual(Comment comment, Comment actualComment) {
        isCommentsEqual(comment, actualComment, false);
    }

    private void isCommentsEqual(Comment comment, Comment actualComment, boolean skipDate) {
        PostDAOImplIntegrationTest.verifyPost(comment.getPost(), actualComment.getPost());
        UserDAOImplIntegrationTest.verifyAddedUser(comment.getUser(), actualComment.getUser());

        assertEquals(comment.getContent(), actualComment.getContent());

        if (!skipDate) {
            assertEquals(comment.getCommentTime(), actualComment.getCommentTime());
        }
    }

}



