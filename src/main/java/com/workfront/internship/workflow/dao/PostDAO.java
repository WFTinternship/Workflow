package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.domain.Post;

import java.util.List;

/**
 * Created by nane on 5/27/17
 */

public interface PostDAO {
    /**
     *
     * @param post is to be added to the database
     * @return the generated id of added post
     */
    long add(Post post);

    /**
     *
     * @return list of all posts starting with the most recent one
     */
    List<Post> getAll();

    /**
     *
     * @param userId id of the user
     * @return all List of posts created by the specified user
     */
    List<Post> getByUserId(long userId);

    /**
     * Gets posts with the specified app area id
     * @param id id of the app area
     * @return List of posts created under the specified app area
     */
    List<Post> getByAppAreaId(long id);

    /**
     *
     * @param title the phrase to search for posts
     * @return List of Post that contain the parameter in their titles
     */
    List<Post> getByTitle(String title);

    /**
     *
     * @param id of the the post to be retrieved from database
     * @return post with the specified id
     */
    Post getById(long id);

    /**
     *
     * @param postId id of the post
     * @return List of answers of the specified post
     */
    List<Post> getAnswersByPostId(long postId);


    /**
     *
     * @param postId id of the post
     * @return the best answer of the specified post
     */
    Post getBestAnswer(long postId);

    /**
     *
     * @param postId id of the post whose best answer is to be set
     * @param answerId id of the answer which is the best one for the post
     */
    void setBestAnswer(long postId, long answerId);

    /**
     *
     * @param post the post whose title and content can be updated
     */
    void update(Post post);

    /**
     *
     * @param id of the post to be deleted from database
     */
    void delete(long id);


}
