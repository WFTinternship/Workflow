package com.workfront.internship.dao;

import com.workfront.internship.dataModel.Post;

import java.util.List;

/**
 * Created by nane on 5/27/17.
 */
public interface PostDAO {

    boolean add(Post post);

    boolean update(Post post);

    boolean delete(long id);

    List<Post> getAll();

    List<Post> getByUserId(long userId);

    List<Post> getByTitle(String title);

}
