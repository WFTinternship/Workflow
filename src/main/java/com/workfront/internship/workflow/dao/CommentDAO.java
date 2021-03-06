package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.dao.impl.CommentDAOImpl;

import java.util.List;


public interface CommentDAO {

    String id = "id";
    String userId = "user_id";
    String postId = "post_id";
    String content = "content";
    String dateTime = "comment_time";

    // CREATE

    /**
     * adds comment to the database
     * @see CommentDAOImpl#add
     * @param comment
     * '@return'
     */
    long add(Comment comment);

    // READ

    /**
     * returns the comment from database with the given id
     * @see CommentDAOImpl#getById(long)
     * @param id
     * '@return'
     */
    Comment getById(long id);

    /**
     *
     * @param postId id of the post
     * @return List of comments of the specified post
     */
    List<Comment> getByPostId(long postId);

    /**
     * returns all comments from database as a list
     * @see CommentDAOImpl#getAll()
     * '@return'
     */
    List<Comment> getAll();

    // UPDATE

    /**
     * updates the comment from database with given id
     * @see CommentDAOImpl#update(long, String)
     * '@param' id
     * @param newComment
     * '@return'
     */
    boolean update(long id , String newComment);

    // DELETE

    /**
     * deletes the comment with the given id
     * @see CommentDAOImpl#delete(long)
     * @param id
     * '@return'
     */
    void delete(long id);


}
