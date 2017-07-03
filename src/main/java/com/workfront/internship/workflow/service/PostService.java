package com.workfront.internship.workflow.service;


import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;

import javax.jws.soap.SOAPBinding;
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
     * Gets all post which titles contain the specified answerTitle string.
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
     * Gets posts with the specified app area id
     * @param id id of the app area
     * @return List of posts created under the specified app area
     */
    List<Post> getByAppAreaId(long id);

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
     * Updates the answerTitle, postContent of both of the post.
     * @param post
     * @return
     */
    void update(Post post);

    /**
     * increases number of likes of the post with the given id by one
     * @param id
     */
    void like(long id);

    /**
     * increases number of dislikes of the post with the given id by one
     * @param id
     */
    void dislike(long id);

    //DELETE

    /**
     * Deletes a post with the specified id.
     * @param id
     * @return
     */
    void delete(long id);

    Integer getNumberOfAnswers(long postId);

    /**
     * Notifies the user with specifies userId when there
     * is response to the post with specified id
     * @param postId
     * @param userId
     */
    void getNotified(long postId, long userId);

    /**
     * Gets all user that should be notified when there
     * is response to the post with specified id
     * @param postId
     * @return
     */
    List<User> getNotificationRecipients(long postId);
}
