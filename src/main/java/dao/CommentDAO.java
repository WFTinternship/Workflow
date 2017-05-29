package dao;

import dataModel.Comment;

import java.util.List;

/**
 * Created by nane on 5/27/17.
 */
public interface CommentDAO {

    boolean  addComment(Comment comment);

    boolean updateComment(long id, String content);

    boolean deleteComment(long id);

    List<Comment> getComments(long postId);
}
