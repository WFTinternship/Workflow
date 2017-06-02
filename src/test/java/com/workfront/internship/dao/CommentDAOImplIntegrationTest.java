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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
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
    public void setup() {

        userDAO = new UserDAOImpl();
        user=  DaoTestUtil.getRandomUser();
      //  userDAO.add(user);

        appAreaDAO = new AppAreaDAOImpl();
        appArea = DaoTestUtil.getRandomAppArea();

        postDAO = new PostDAOImpl();
        post=  DaoTestUtil.getRandomPost(user, appArea);
       // postDAO.add(post);

        commentDAO = new CommentDAOImpl();
        comment = DaoTestUtil.getRandomComment(user, post);

    }

    @After
    public void tearDown() {
       /* for (Comment c : commentList) {
            commentDAO.delete(c.getId());
        }
        userDAO.deleteById(user.getId());
        postDAO.delete(post.getId());
        appAreaDAO.deleteById(appArea.getId());
        commentDAO.delete(comment.getId());*/

    }

    /**
     * @see CommentDAO#add(Comment)
     */
    @Test
    public void add_success() {
        long expectedCommentId = commentDAO.add(comment);
        Comment actualComment = commentDAO.getById(expectedCommentId);
        isCommentsEqual(comment, actualComment,false);
    }

    @Test(expected = RuntimeException.class)
    public void add_failure() {
        comment.setPost(null);
        long commentId = commentDAO.add(comment);
        Comment comment = commentDAO.getById(commentId);
        assertNull(comment);
    }

    /**
     * @see CommentDAO#update(Comment)
     */
    @Test
    public void update_success() {
        Comment otherComment = DaoTestUtil.getRandomComment(user, post);

        Long id = commentDAO.add(otherComment);
        assertNotNull(id);
        assertTrue(id > 0);
        String newComment = "some new comment";
        otherComment.setContent(newComment);
        // Test method
        boolean updated = commentDAO.update(otherComment);
        assertTrue(updated);

        // acquire stored/updated comment
        Comment updatedComment = commentDAO.getById(otherComment.getId());

        isCommentsEqual(otherComment, updatedComment, false);
    }

    @Test(expected = RuntimeException.class)
    public void update_failure() {

        Comment otherComment = DaoTestUtil.getRandomComment( user , post ) ;

        long id = commentDAO.add(otherComment) ;
        assertNotNull(id);
        assertTrue(id>0);
        otherComment.setContent(null);
        commentDAO.update(otherComment);
        //test method
        Comment updateComment = commentDAO.getById(otherComment.getId()) ;
        assertNull(updateComment);
    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Test
    public void delete_success() {
        Comment comment = DaoTestUtil.getRandomComment(user,post) ;

        long commentId = commentDAO.add(comment);
        commentDAO.delete(commentId);
        assertNull(commentDAO.getById(commentId));

    }

    @Test
    public void delete_failure(){
        Comment comment = DaoTestUtil.getRandomComment(user,post) ;

        long commentId = commentDAO.add(comment);
        int n = commentDAO.delete(commentId+1000000);
        assertEquals(n,0);


    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Test
    public void getById_success(){
    long commentId = commentDAO.add(comment);
    Comment actualComment = commentDAO.getById(commentId);
        isCommentsEqual(comment,actualComment,false);

    }
    @Test
    public void getById_failure() {
        Comment comment = commentDAO.getById(1000000) ;
        assertEquals(comment,null);
    }

    /**
     * @see CommentDAO#getAll()
     */
    @Test
    public void getAll_success(){
        List<Comment> allComments = commentDAO.getAll();
        int size = allComments.size();
        assertNotNull(allComments);

        Comment comment = DaoTestUtil.getRandomComment(user,post) ;
        commentDAO.add(comment);
        Comment anotherComment = DaoTestUtil.getRandomComment(user,post) ;
        commentDAO.add(anotherComment);
        allComments = commentDAO.getAll() ;
        assertNotNull(allComments);
        assertTrue(allComments.size() == size+2 && allComments.contains(comment) &&
                allComments.contains(anotherComment));


    }

    /*private void isCommentsEqual(Comment comment, Comment actualComment) {
        isCommentsEqual(comment, actualComment, true);
    }*/

    private void isCommentsEqual(Comment comment, Comment actualComment, boolean skipDate) {
        PostDAOImplIntegrationTest.verifyPost(comment.getPost(), actualComment.getPost());
        UserDAOImplIntegrationTest.verifyAddedUser(comment.getUser(), actualComment.getUser());

        assertEquals(comment.getContent(), actualComment.getContent());

        if (skipDate) {
            assertEquals(comment.getCommentTime(), actualComment.getCommentTime());
        }
    }

}



