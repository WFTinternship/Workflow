package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.PostService;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by nane on 6/4/17
 */
public class PostServiceImpl implements PostService{

    private static final Logger logger = Logger.getLogger(PostDAOImpl.class);

    private PostDAO postDAO = new PostDAOImpl();

    /**
     * @see PostDAOImpl#add(Post)
     */
    @Override
    public long add(Post post) {
        if (!post.isValid()){
            logger.error("Post is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Post");
        }
        long id;
        try{
            id = postDAO.add(post);
        }catch (RuntimeException e){
            logger.error("Failed to add the post to database");
            throw new ServiceLayerException("Failed to add the post to database", e);
        }
        return id;
    }

    /**
     * @see PostDAOImpl#setBestAnswer(long, long)
     */
    @Override
    public void setBestAnswer(long postId, long answerId) {
        if (postId < 1) {
            logger.error("Post id is not valid");
            throw new InvalidObjectException("Invalid post id");
        }
        if (answerId < 1) {
            logger.error("Answer id is not valid");
            throw new InvalidObjectException("Invalid answer id");
        }
        try {
            postDAO.setBestAnswer(postId, answerId);
        }catch (RuntimeException e){
            logger.error("Failed during setting the best answer");
            throw new ServiceLayerException("Failed during setting the best answer", e);
        }
    }

    /**
     * @see PostDAOImpl#getAll()
     */
    @Override
    public List<Post> getAll() {
        List<Post> posts;
        try {
            posts = postDAO.getAll();
        }catch (RuntimeException e){
            logger.error(e.getStackTrace());
            throw new ServiceLayerException("Failed to get all posts");
        }
        if (posts == null){
            logger.info("No posts were found");
            return null;
        }
        return posts;
    }

    /**
     * @see PostDAOImpl#getById(long)
     */
    @Override
    public Post getById(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Invalid post id");
        }
        Post post;
        try {
            post = postDAO.getById(id);
        }catch (RuntimeException e){
            logger.error(e.getStackTrace());
            throw new ServiceLayerException("Failed to get post with specified id");
        }
        if (post == null){
            logger.info("No post was found with specified id");
            return null;
        }
        return post;
    }

    /**
     * @see PostDAOImpl#getByTitle(String)
     */
    @Override
    public List<Post> getByTitle(String title) {
        if (isEmpty(title)){
            logger.error("Title is not valid");
            throw new InvalidObjectException("Not valid title");
        }
        List<Post> posts;
        try {
            posts = postDAO.getByTitle(title);
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new ServiceLayerException("Failed to get posts by specified title");
        }
        return posts;
    }

    /**
     * @see PostDAOImpl#getByUserId(long)
     */
    @Override
    public List<Post> getByUserId(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> posts;
        try {
            posts = postDAO.getByUserId(id);
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new ServiceLayerException("Failed to get posts by specified user id");
        }
        if (posts == null){
            logger.info("No post was found with specified user id");
            return null;
        }
        return posts;
    }

    /**
     * @see PostDAOImpl#getAnswersByPostId(long)
     */
    @Override
    public List<Post> getAnswersByPostId(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> posts;
        try {
            posts = postDAO.getAnswersByPostId(id);
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new ServiceLayerException("Failed to get answers of the specified post");
        }
        if (posts == null){
            logger.warn("Post with the specified id does not have answers");
            return null;
        }
        return posts;
    }

    /**
     * @see PostDAOImpl#getBestAnswer(long)
     */
    @Override
    public Post getBestAnswer(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        Post post;
        try {
            post = postDAO.getBestAnswer(id);
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new ServiceLayerException("Failed to get the best answer of the specified post");
        }
        if (post == null){
            logger.warn("Post with specified id does not have a best answer");
            return null;
        }
        return post;
    }

    /**
     * @see PostDAOImpl#update(Post)
     */
    @Override
    public void update(Post post) {
        if (!post.isValid()){
            logger.error("Post is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Post");
        }
        try {
            postDAO.update(post);
        }catch (RuntimeException e){
            logger.error("Failed to update the post");
            throw new ServiceLayerException("Failed to update the post", e);
        }
    }

    /**
     * @see PostDAOImpl#delete(long)
     */
    @Override
    public void delete(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        try {
            postDAO.delete(id);
        }catch (RuntimeException e){
            logger.error("Failed to delete specified posts");
            throw new ServiceLayerException("Failed to delete specified posts", e);
        }
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
