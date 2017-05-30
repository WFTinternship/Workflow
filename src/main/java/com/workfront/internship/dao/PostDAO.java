package com.workfront.internship.dao;

import com.workfront.internship.dataModel.Post;

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

    boolean update(Post post);

    boolean delete(long id);


}
