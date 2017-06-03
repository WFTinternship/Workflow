package com.workfront.internship.dao;

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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;


/**
 * Created by angel on 30.05.2017
 */
public class CommentDAOImplIntegrationTest {
    private Comment comment;
    private CommentDAO commentDAO;

    private User user;
    private UserDAO userDAO;

    private Post post;
    private PostDAO postDAO;

    private AppArea appArea;

    List<Comment> commentList = new ArrayList<>();


    @Before
    public void setup() {
        commentList = new ArrayList<>();

        userDAO = new UserDAOImpl();
        user=  DaoTestUtil.getRandomUser();
        userDAO.add(user);

        appArea = DaoTestUtil.getRandomAppArea();

        postDAO = new PostDAOImpl();
        post=  DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(post);

        commentDAO = new CommentDAOImpl();
        comment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(comment);

    }

    @After
    public void tearDown() {
        for (Comment c : commentList) {
            commentDAO.delete(c.getId());
        }
        userDAO.deleteById(user.getId());
        postDAO.delete(post.getId());
    }


    // region <TEST CASE>


    /**
     * @see CommentDAO#add(Comment)
     */
    @Test
    public void add_success() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(otherComment);

        // Test method
        long expectedCommentId = commentDAO.add(otherComment);
        assertNotNull(expectedCommentId);
        assertTrue(expectedCommentId > 0);

        // acquire added comment
        Comment actualComment = commentDAO.getById(expectedCommentId);
        assertNotNull(actualComment);

        isCommentsEqual(otherComment, actualComment,false);
    }

    @Test(expected = RuntimeException.class)
    public void add_failure() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(otherComment);
        otherComment.setContent(null);
        // Test method
        long commentId = commentDAO.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        Comment actualComment = commentDAO.getById(commentId);
        assertNull(actualComment);
    }

    /**
     * @see CommentDAO#update(long, String)
     */
    @Test
    public void update_success() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(otherComment);

        String newContent = "some new comment";

        Long id = commentDAO.add(otherComment);
        assertNotNull(id);
        assertTrue(id > 0);

        otherComment.setContent(newContent);

        // Test method
        boolean updated = commentDAO.update(otherComment.getId() , newContent);
        assertTrue(updated);

        // acquire stored/updated comment
        Comment updatedComment = commentDAO.getById(otherComment.getId());
        isCommentsEqual(otherComment, updatedComment, false);
    }

    @Test
    public void update_failure() {
        Comment otherComment = DaoTestUtil.getRandomComment( user , post ) ;
        commentList.add(otherComment);

        long id = commentDAO.add(otherComment) ;
        assertNotNull(id);
        assertTrue(id > 0 );

        // Test method
        boolean b = commentDAO.update(otherComment.getId() , null );
        assertFalse(b);

    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Test
    public void delete_success() {
        Comment otherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentList.add(otherComment);

        long commentId = commentDAO.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        // Test method
        commentDAO.delete(commentId);
        assertNull(commentDAO.getById(commentId));

    }

    @Test
    public void delete_failure() {
        Comment otherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentList.add(otherComment);

        long commentId = commentDAO.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        // Test method
        int n = commentDAO.delete(commentId+1000000);
        assertEquals(n,0);
    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Test
    public void getById_success() {
        long commentId = commentDAO.add(comment);

        // Test method
        Comment actualComment = commentDAO.getById(commentId);
        assertNotNull(actualComment);

        isCommentsEqual(comment,actualComment,false);
    }
    @Test
    public void getById_failure() {
        // Test method
        Comment comment = commentDAO.getById(1000000) ;
        assertEquals(comment,null);
    }

    /**
     * @see CommentDAO#getAll()
     */
    @Test
    public void getAll_success(){
        List<Comment> otherList = commentDAO.getAll();
        int size = otherList.size();
        assertNotNull(otherList);

        Comment otherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentDAO.add(otherComment);

        commentList.add(otherComment);

        Comment anotherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentDAO.add(anotherComment);

        commentList.add(anotherComment);

        otherList = commentDAO.getAll() ;
        assertNotNull(otherList);
        assertTrue(otherList.size() == size + 2 && otherList.contains(otherComment) &&
                otherList.contains(anotherComment));


    }

    // endregion

    // region <HELPERS>

    private void isCommentsEqual(Comment comment, Comment actualComment, boolean skipDate) {
        PostDAOImplIntegrationTest.verifyPost(comment.getPost(), actualComment.getPost());
        UserDAOImplIntegrationTest.verifyAddedUser(comment.getUser(), actualComment.getUser());

        assertEquals(comment.getContent(), actualComment.getContent());

        if (skipDate) {
            assertEquals(comment.getCommentTime(), actualComment.getCommentTime());
        }
    }

    // endregion

}



