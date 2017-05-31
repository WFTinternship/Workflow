package com.workfront.internship.dao;

import com.workfront.internship.dataModel.Comment;

import java.util.List;


public interface CommentDAO {

    boolean  addComment(Comment comment);

    boolean updateComment(long id, String content);

    boolean deleteComment(long id);

    List<Comment> getComments(long postId);
}
