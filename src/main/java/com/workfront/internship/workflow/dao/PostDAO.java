package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;

import java.util.List;

/**
 * Created by nane on 5/27/17
 */

public interface PostDAO {
    // Post fileds
    String id = "id";
    String postId = "post_id";
    String userId = "user_id";
    String appAreaId = "apparea_id";
    String postTime = "post_time";
    String postContent = "content";
    String postTitle = "title";
    String likesNumber = "likes_number";
    String dislikesNumber = "dislikes_number";

    // Answer fields

    String answerTime = "answer_time";
    String answerContent = "answer_content";
    String answerTitle = "answer_title";

    // Parent post fields

    String parentId = "parentId";

    String parentUserId = "parentUserId";
    String parentUserFirstName = "parentUserFirstName";
    String parentUserLastName = "parentUserLastName";
    String parentUserEmail = "parentUserEmail";
    String parentUserPasscode = "parentUserPasscode";
    String parentUserAvatar = "parentUserAvatar";
    String parentUserRating = "parentUserRating";

    String parentAppAreaId = "parentAppAreaId";

    String parentTime = "parentTime";
    String parentContent = "parentContent";
    String parentTitle = "parentTitle";

    /**
     * Adds post to the db
     * @param post is to be added to the database
     * @return the generated id of added post
     */
    long add(Post post);

    /**
     * Gets list of all posts form the db
     * @return list of all posts starting with the most recent one
     */
    List<Post> getAll();

    /**
     * Gets list of posts posted by the user with the specified userId
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
     * Gets list of posts which titles contain the specified title string.
     * @param title the phrase to search for posts
     * @return List of Post that contain the parameter in their titles
     */
    List<Post> getByTitle(String title);

    /**
     * Gets post with specified id
     * @param id of the the post to be retrieved from database
     * @return post with the specified id
     */
    Post getById(long id);

    /**
     * Gets list of answers of the post by the specified postId
     * @param postId id of the post
     * @return List of answers of the specified post
     */
    List<Post> getAnswersByPostId(long postId);

    /**
     * Gets the answer of the post specified by postId, that was marked as best answer
     * @param postId id of the post
     * @return the best answer of the specified post
     */
    Post getBestAnswer(long postId);

    /**
     * gets number of likes of the post with the specified id
     * @param postId
     * @return
     */
    long getLikesNumber(long postId);

    /**
     * gets number of dislikes of the post with the specified id
     * @param postId
     * @return
     */
    long getDislikesNumber(long postId);

    /**
     * Sets the answer as the best one for the specified post
     * @param postId id of the post whose best answer is to be set
     * @param answerId id of the answer which is the best one for the post
     */
    void setBestAnswer(long postId, long answerId);

    /**
     * Updates fields of the specified post
     * @param post the post whose fields should be updated
     */
    void update(Post post);

    /**
     *
     * user with specified id likes the post with the specified id
     * @param userId
     * @param postId
     */
    void like(long userId, long postId);

    /**
     *
     * user with specified id dislikes the post with the specified id
     * @param userId
     * @param postId
     */
    void dislike(long userId, long postId);

    /**
     * Deletes the post with the specified id
     * @param id of the post to be deleted from database
     */
    void delete(long id);

    /**
     * Gets number of answers of the specified postId
     * @param postId of the post which number of answers should get
     */
    Integer getNumberOfAnswers(long postId);

    /**
     * Notifies the user with specifies userId when there
     * is response to the post with specified postId
     * @param postId the id of a post that the user wants to be notified
     * @param userId the id of a user that will be notified
     */
    void getNotified(long postId, long userId);

    /**
     * Gets all user that should be notified when there
     * is response to the post with specified id
     * @param postId the id of a post
     * @return List of users that need to be notified for the specified post
     */
    List<User> getNotificationRecipients(long postId);

    /**
     * Removes the answer from best answer
     * @param answerId
     */
    void removeBestAnswer(long answerId);
}
