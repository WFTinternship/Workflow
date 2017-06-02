package com.workfront.internship.dao;

import com.workfront.internship.dataModel.Comment;

import java.util.List;


public interface CommentDAO {

    long add(Comment comment);

    boolean update(Comment comment);

    int delete(long id);

    Comment getById(long id);

    List<Comment> getAll();
}
