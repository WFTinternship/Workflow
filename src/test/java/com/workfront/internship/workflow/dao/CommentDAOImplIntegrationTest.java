package com.workfront.internship.workflow.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.dao.impl.CommentDAOImpl;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.dao.impl.UserDAOImpl;
import com.workfront.internship.workflow.dao.springJDBC.CommentDAOSpringImpl;
import com.workfront.internship.workflow.dao.springJDBC.PostDAOSpringImpl;
import com.workfront.internship.workflow.dao.springJDBC.UserDAOSpringImpl;
import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;

import com.workfront.internship.workflow.util.DaoTestUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.*;


/**
 * Created by Angel on 30.05.2017
 */
public class CommentDAOImplIntegrationTest  extends BaseIntegrationTest{
    private Comment comment;
    private CommentDAOSpringImpl commentDAOSpring;

    private User user;
    private UserDAOSpringImpl userDAOSpring;

    private Post post;
    private PostDAOSpringImpl postDAOSpring;

    private List<Comment> commentList = new ArrayList<>();


    @Before
    public void setup() {
        AppArea appArea;
        commentList = new ArrayList<>();

        userDAOSpring = new UserDAOSpringImpl(dataSource);
        user=  DaoTestUtil.getRandomUser();
        userDAOSpring.add(user);

        appArea = DaoTestUtil.getRandomAppArea();

        postDAOSpring = new PostDAOSpringImpl(dataSource);
        post=  DaoTestUtil.getRandomPost(user, appArea);
        postDAOSpring.add(post);

        commentDAOSpring = new CommentDAOSpringImpl(dataSource);
        comment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(comment);

        LOG = Logger.getLogger(CommentDAOImplIntegrationTest.class);
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @After
    public void tearDown() {
        for (Comment c : commentList) {
            commentDAOSpring.delete(c.getId());
        }
        userDAOSpring.deleteById(user.getId());
        postDAOSpring.delete(post.getId());

        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
        long expectedCommentId = commentDAOSpring.add(otherComment);
        assertNotNull(expectedCommentId);
        assertTrue(expectedCommentId > 0);

        // acquire added comment
        Comment actualComment = commentDAOSpring.getById(expectedCommentId);
        assertNotNull(actualComment);

        isCommentsEqual(otherComment, actualComment,false);
    }

    @Test(expected = RuntimeException.class)
    public void add_failure() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(otherComment);
        otherComment.setContent(null);
        // Test method
        long commentId = commentDAOSpring.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        Comment actualComment = commentDAOSpring.getById(commentId);
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

        Long id = commentDAOSpring.add(otherComment);
        assertNotNull(id);
        assertTrue(id > 0);

        otherComment.setContent(newContent);

        // Test method
        boolean updated = commentDAOSpring.update(otherComment.getId() , newContent);
        assertTrue(updated);

        // acquire stored/updated comment
        Comment updatedComment = commentDAOSpring.getById(otherComment.getId());
        isCommentsEqual(otherComment, updatedComment, false);
    }

    @Test
    public void update_failure() {
        Comment otherComment = DaoTestUtil.getRandomComment( user , post ) ;
        commentList.add(otherComment);

        long id = commentDAOSpring.add(otherComment) ;
        assertNotNull(id);
        assertTrue(id > 0 );

        // Test method
        boolean b = commentDAOSpring.update(otherComment.getId() , null );
        assertFalse(b);

    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Test
    public void delete_success() {
        Comment otherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentList.add(otherComment);

        long commentId = commentDAOSpring.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        // Test method
        commentDAOSpring.delete(commentId);
        assertNull(commentDAOSpring.getById(commentId));

    }

    @Test
    public void delete_failure() {
        Comment otherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentList.add(otherComment);

        long commentId = commentDAOSpring.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        // Test method
        commentDAOSpring.delete(commentId+1000000);
    }

    /**
     * @see CommentDAOImpl#getByPostId(long)
     */
    @Test
    public void getByPostId_success(){
        Comment comment = DaoTestUtil.getRandomComment(user,post);
        commentDAOSpring.add(comment);
        // Test method
        List<Comment> actualComments = commentDAOSpring.getByPostId(comment.getPost().getId());
        assertNotNull(actualComments);

        assertTrue(actualComments.contains(comment));
    }

    /**
     * @see CommentDAOImpl#getByPostId(long)
     */
    @Test
    public void getByPostId_failure(){
        // Test method
        List<Comment> actualComments = commentDAOSpring.getByPostId(100000000);
        assertNotNull(actualComments);

        assertTrue(actualComments.isEmpty());
    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Test
    public void getById_success() {
        Comment comment = DaoTestUtil.getRandomComment(user,post);
        long commentId = commentDAOSpring.add(comment);

        // Test method
        Comment actualComment = commentDAOSpring.getById(commentId);
        assertNotNull(actualComment);

        commentDAOSpring.add(actualComment);

        isCommentsEqual(comment,actualComment,false);
    }
    @Test
    public void getById_failure() {
        // Test method
        Comment comment = commentDAOSpring.getById(1000000) ;
        assertEquals(comment,null);
    }

    /**
     * @see CommentDAO#getAll()
     */
    @Test
    public void getAll_success(){
        List<Comment> otherList = commentDAOSpring.getAll();
        int size = otherList.size();
        assertNotNull(otherList);

        Comment otherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentDAOSpring.add(otherComment);

        commentList.add(otherComment);

        Comment anotherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentDAOSpring.add(anotherComment);

        commentList.add(anotherComment);

        otherList = commentDAOSpring.getAll() ;
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



