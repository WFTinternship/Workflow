package com.workfront.internship.workflow.service.impl;

import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.dao.impl.CommentDAOImpl;
import com.workfront.internship.workflow.domain.Comment;
import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.CommentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Created by Angel on 6/5/2017
 */
public class CommentServiceImpl implements CommentService {

    private  static final Logger logger = Logger.getLogger(CommentDAOImpl.class);

    private final CommentDAO commentDAO;

    @Autowired
    public CommentServiceImpl(@Qualifier("commentDAOSpring") CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    /**
     *@see CommentDAOImpl#add(Comment)
     */
    @Override
    public long add(Comment comment) {
        if(comment == null || !comment.isValid()) {
            logger.error("Comment is invalid. Failed to add to the database");
            throw new InvalidObjectException("Invalid Comment");
        }
        long id;
        try {
            id = commentDAO.add(comment);
        }catch (RuntimeException e) {
            logger.error("Failed to add the comment to database");
            throw new ServiceLayerException("Failed to add the comment to database", e);
        }
        return id;
    }

    /**
     *@see CommentDAOImpl#getById(long)
     */
    @Override
    public Comment getById(long id) {
        if(id < 1 ) {
            logger.error("Id is invalid");
            throw new InvalidObjectException("Invalid id");
        }
        Comment comment ;
        try {
           comment = commentDAO.getById(id);
        }catch(RuntimeException e) {
            logger.error("Failed to get the comment by id from database!");
            throw new ServiceLayerException("Failed to get comment with specified id");
        }
        return comment;
    }

    /**
     * @see CommentDAOImpl#getAll()
     */
    @Override
    public List<Comment> getAll() {
        try {
            return commentDAO.getAll();
        } catch (RuntimeException e) {
            logger.error(e.getStackTrace());
            throw new ServiceLayerException("Failed to get all comments");
        }
    }

    /**
     *@see CommentDAOImpl#getByPostId(long)
     */
    @Override
    public List<Comment> getByPostId(long id) {
        if(id < 1 ) {
            logger.error("Id is invalid !");
            throw new InvalidObjectException("Invalid id !");
        }
        try {
            return commentDAO.getByPostId(id);
        }catch(RuntimeException e) {
            logger.error("Failed to get comments by the specified post id!");
            throw new ServiceLayerException("Failed to get comments with specified id");
        }
    }

    /**
     *@see CommentDAOImpl#update(long, String)
     */
    @Override
    public boolean update(long id, String newContent) {
        if( id < 1 || newContent == null){
            logger.error(" There is no such comment for update !");
            throw new InvalidObjectException();
        }
        try{
            return commentDAO.update(id,newContent);
        }catch(RuntimeException e) {
            logger.error("Failed to update the comment !");
            throw new ServiceLayerException("Failed to update comment");
        }
    }

    /**
     *@see CommentDAOImpl#delete(long)
     */
    @Override
    public void delete(long id) {
        if (id < 1) {
            logger.error("Id is invalid");
            throw new InvalidObjectException("Invalid id");
        }
        try {
             commentDAO.delete(id);
        } catch(RuntimeException e){
            logger.error("Failed to delete the comment");
            throw new ServiceLayerException("Failed to delete comment");
        }
    }
}