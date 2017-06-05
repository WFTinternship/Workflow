package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.dao.impl.PostDAOImpl;
import com.workfront.internship.workflow.dataModel.Post;
import com.workfront.internship.workflow.exceptions.InvalidObjectException;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.util.Validator;
import javafx.geometry.Pos;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by nane on 6/4/17. 
 */
public class PostServiceImpl implements PostService{

    private static final Logger logger = Logger.getLogger(PostDAOImpl.class);

    private PostDAO postDAO = new PostDAOImpl();

    @Override
    public long add(Post post) {
        if (!Validator.isValidPost(post)){
            logger.error("Post is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Post");
        }
        long id = 0;
        try{
            id = postDAO.add(post);
        }catch (RuntimeException e){
            logger.error("Failed to add the post to database");
        }
        return id;
    }

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
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = postDAO.getAll();
        if (posts == null){
            logger.warn("No posts were found");
            return null;
        }
        return posts;
    }

    @Override
    public Post getById(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Invalid post id");
        }
        Post post = postDAO.getById(id);
        if (post == null){
            logger.warn("No post was found with specified id");
            return null;
        }
        return post;
    }

    @Override
    public List<Post> getByTitle(String title) {
        if (Validator.isEmpty(title)){
            logger.error("Title is not valid");
            throw new InvalidObjectException("Not valid title");
        }
        List<Post> posts = postDAO.getByTitle(title);
        if (posts == null){
            logger.warn("No post was found with specified title");
            return null;
        }
        return posts;
    }

    @Override
    public List<Post> getByUserId(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> posts = postDAO.getByUserId(id);
        if (posts == null){
            logger.warn("No post was found with specified user id");
            return null;
        }
        return posts;
    }

    @Override
    public List<Post> getAnswersByPostId(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        List<Post> posts = postDAO.getAnswersByPostId(id);
        if (posts == null){
            logger.warn("Post with the specified id does not have answers");
            return null;
        }
        return posts;
    }

    @Override
    public Post getBestAnswer(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
        Post post = postDAO.getBestAnswer(id);
        if (post == null){
            logger.warn("Post with specified id does not have a best answer");
            return null;
        }
        return post;
    }

    @Override
    public void update(Post post) {
        if (!Validator.isValidPost(post)){
            logger.error("Post is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Post");
        }
        try {
            postDAO.update(post);
        }catch (RuntimeException e){
            logger.error("Failed to update the post");
        }
    }

    @Override
    public int delete(long id) {
        if (id < 1){
            logger.error("Id is not valid");
            throw new InvalidObjectException("Not valid id");
        }
         return postDAO.delete(id);
    }
}
