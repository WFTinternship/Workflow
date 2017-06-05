package com.workfront.internship.workflow.service;


import com.workfront.internship.workflow.dataModel.Post;
import javafx.geometry.Pos;
import org.omg.PortableServer.POA;

import java.util.List;

public interface PostService {

    //CREATE

    /**
     * Adds a post
     * @param post
     * @return the id of added post
     */
    long add(Post post);

    /**
     * Sets the specified answer as best one for the specified post
     * @param postId
     * @param answerId
     * @return <tt>true</tt> if the answer was successfully set.
     */
    void setBestAnswer(long postId, long answerId);

    //READ

    /**
     * Gets all post
     * @return List of posts
     */
    List<Post> getAll();

    /**
     * Gets a Post by id.
     * @param id
     * @return Post with id
     */
    Post getById(long id);

    /**
     * Gets all post which titles contain the specified title string.
     * @param title
     * @return
     */
    List<Post> getByTitle(String title);

    /**
     * Gets all posts of a specified user
     * @param id
     * @return all posts of the specified user.
     */
    List<Post> getByUserId(long id);

    /**
     * Gets all answers of the post by id.
     * @param id
     * @return List of answers
     */
    List<Post> getAnswersByPostId(long id);

    /**
     * Gets best answer of the post by id.
     * @param id
     * @return Best answer
     */
    Post getBestAnswer(long id);

    //UPDATE

    /**
     * Updates the title, content of both of the post.
     * @param post
     * @return
     */
    void update(Post post);

    //DELETE

    /**
     * Deletes a post with the specified id.
     * @param id
     * @return
     */
    void delete(long id);
}
