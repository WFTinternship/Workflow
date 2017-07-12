package com.workfront.internship.workflow.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.util.DaoTestUtil;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;


/**
 * Created by Angel on 30.05.2017
 */
public class CommentDAOIntegrationTest extends BaseIntegrationTest {

    @Autowired
    @Qualifier("commentDAOHibernateImpl")
    private CommentDAO commentDAO;

    @Autowired
    @Qualifier("userDAOHibernateImpl")
    private UserDAO userDAO;

    @Autowired
    @Qualifier("postDAOHibernateImpl")
    private PostDAO postDAO;

    private Comment comment;
    private User user;
    private Post post;

    private List<Comment> commentList = new ArrayList<>();

    @Before
    public void setup() {
        AppArea appArea;
        commentList = new ArrayList<>();

        user = DaoTestUtil.getRandomUser();
        userDAO.add(user);

        appArea = DaoTestUtil.getRandomAppArea();

        post = DaoTestUtil.getRandomPost(user, appArea);
        postDAO.add(post);

        comment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(comment);

        LOG = Logger.getLogger(CommentDAOIntegrationTest.class);
        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    // region <TEST CASE>

    @After
    public void tearDown() {
        for (Comment comment : commentList) {
            commentDAO.delete(comment.getId());
        }
        userDAO.deleteById(user.getId());
        postDAO.delete(post.getId());

        if (dataSource instanceof ComboPooledDataSource) {
            try {
                LOG.info(((ComboPooledDataSource) dataSource).getNumBusyConnections());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @see CommentDAO#add(Comment)
     */
    @Test
    @Transactional
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

        isCommentsEqual(otherComment, actualComment, false);
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
     * @see CommentDAO#update(Comment)
     */
    @Test
    public void update_success() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(otherComment);

        String newContent = "some new content";

        Long id = commentDAO.add(otherComment);
        assertNotNull(id);
        assertTrue(id > 0);

        otherComment.setContent(newContent);

        // Test method
        commentDAO.update(comment);

        // acquire stored/updated comment
        Comment updatedComment = commentDAO.getById(otherComment.getId());
        isCommentsEqual(otherComment, updatedComment, false);
    }

    /**
     * @see CommentDAO#update(Comment)
     */
    @Test(expected = RuntimeException.class)
    public void update_failure() {
        Comment comment = DaoTestUtil.getRandomComment(user, post);
        comment.setContent(null);

        // Test method
        commentDAO.update(comment);
    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Test
    public void delete_success() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(otherComment);

        long commentId = commentDAO.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        // Test method
        commentDAO.delete(commentId);
        assertNull(commentDAO.getById(commentId));

    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Test
    public void delete_failure() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentList.add(otherComment);

        long commentId = commentDAO.add(otherComment);
        assertNotNull(commentId);
        assertTrue(commentId > 0);

        // Test method
        commentDAO.delete(commentId + 1000000);
    }

    /**
     * @see CommentDAO#getByPostId(long)
     */
    @Test
    public void getByPostId_success() {
        Comment comment = DaoTestUtil.getRandomComment(user, post);
        commentDAO.add(comment);

        // Test method
        List<Comment> actualComments = commentDAO.getByPostId(comment.getPost().getId());
        assertNotNull(actualComments);

        assertTrue(actualComments.contains(comment));
    }

    /**
     * @see CommentDAO#getByPostId(long)
     */
    @Test
    public void getByPostId_failure() {
        // Test method
        List<Comment> actualComments = commentDAO.getByPostId(100000000);
        assertNotNull(actualComments);

        assertTrue(actualComments.isEmpty());
    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Test
    public void getById_success() {
        Comment comment = DaoTestUtil.getRandomComment(user, post);
        long commentId = commentDAO.add(comment);

        assertNotNull(commentId);

        // Test method
        Comment actualComment = commentDAO.getById(commentId);
        assertNotNull(actualComment);

        isCommentsEqual(comment, actualComment, false);
    }

    @Test
    public void getById_failure() {
        // Test method
        Comment comment = commentDAO.getById(1000000);
        assertEquals(comment, null);
    }

    // endregion

    // region <HELPERS>

    /**
     * @see CommentDAO#getAll()
     */
    @Test
    public void getAll_success() {
        List<Comment> otherList = commentDAO.getAll();
        int size = otherList.size();
        assertNotNull(otherList);

        Comment otherComment = DaoTestUtil.getRandomComment(user, post);
        commentDAO.add(otherComment);

        commentList.add(otherComment);

        Comment anotherComment = DaoTestUtil.getRandomComment(user, post);
        commentDAO.add(anotherComment);

        commentList.add(anotherComment);

        // Test method
        otherList = commentDAO.getAll();
        assertNotNull(otherList);
        assertTrue(otherList.size() == size + 2 && otherList.contains(otherComment) &&
                otherList.contains(anotherComment));

    }

    // endregion

    public static void isCommentsEqual(Comment comment, Comment actualComment, boolean skipDate) {
        PostDAOIntegrationTest.verifyPost(comment.getPost(), actualComment.getPost());
        UserDAOIntegrationTest.verifyAddedUser(comment.getUser(), actualComment.getUser());

        assertEquals(comment.getContent(), actualComment.getContent());

        if (skipDate) {
            assertEquals(comment.getCommentTime(), actualComment.getCommentTime());
        }
    }

}



