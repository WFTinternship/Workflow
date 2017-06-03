package com.workfront.internship.dao;

import com.workfront.internship.dataModel.Comment;
import com.workfront.internship.dao.impl.CommentDAOImpl;

import java.util.List;


public interface CommentDAO {
    /**
     * @see CommentDAOImpl#add(Comment)
     */
    long add(Comment comment);

    /**
     * @see CommentDAOImpl#update(long, String)
     */
    boolean update(long id , String newComment);

    /**
     * @see CommentDAOImpl#delete(long)
     */
    int delete(long id);

    /**
     * @see CommentDAOImpl#getById(long)
     */
    Comment getById(long id);

    /**
     * @see CommentDAOImpl#getAll()
     */
    List<Comment> getAll();
}
