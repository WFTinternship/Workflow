package com.workfront.internship.workflow.service;


import com.workfront.internship.workflow.entity.Comment;

import java.util.List;

public interface CommentService {

    // CREATE

    /**
     * adds comment
     * @see CommentService#add
     * @param comment
     * '@return'
     */
    long add(Comment comment);

    // READ

    /**
     * returns the comment with the given id
     * @see CommentService#getById(long)
     * @param id
     * '@return'
     */
    Comment getById(long id);

    /**
     * returns all comments as a list
     * @see CommentService#getAll()
     * '@return'
     */
    List<Comment> getAll();

    /**
     *
     * @param id id of the post
     * @return List of comments of the specified post
     */
    List<Comment> getByPostId(long id);

    // UPDATE

    /**
     * updates the comment with given id
     * @see CommentService#update(long, String)
     * '@param' id
     * @param newComment
     * '@return'
     */
    boolean update(long id , String newComment);

    // DELETE

    /**
     * deletes the comment with the given id
     * @see CommentService#delete(long)
     * @param id
     * '@return'
     */
    void delete(long id);

}
