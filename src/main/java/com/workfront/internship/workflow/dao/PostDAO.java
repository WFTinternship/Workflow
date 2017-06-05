package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.dataModel.Post;

import java.util.List;

/**
 * Created by nane on 5/27/17.
 */
public interface PostDAO {

    long add(Post post);

    /**
     * Gets all Posts from database.
     *
     * @return
     */
    List<Post> getAll();

    List<Post> getByUserId(long userId);

    List<Post> getByTitle(String title);

    Post getById(long id);

    List<Post> getAnswersByPostId(long postId);

    Post getBestAnswer(long postId);

    void setBestAnswer(long postId, long answerId);

    void update(Post post);

    int delete(long id);


}
