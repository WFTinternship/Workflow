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

    @Override
    public long add(Comment comment) {
        if(comment == null || !comment.isValid()) {
            logger.error("Comment is invalid ! Failed to add to the database");
            throw new InvalidObjectException("Invalid Comment !");
        }
        try {
            return commentDAO.add(comment);
        }catch (RuntimeException e) {
            logger.error("Failed to add the comment to database !");
            return 0;
        }
    }

    @Override
    public Comment getById(long id) {
        if(id < 1 ) {
            logger.error("Id is invalid !");
            throw new InvalidObjectException("Invalid id !");
        }
        try {
           return commentDAO.getById(id);
        }catch(RuntimeException e) {
            logger.error("Failed to get the comment by id from database!");
            return null;
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = commentDAO.getAll();
        if(comments == null) {
            logger.error("No comments were found");
            return null;
        }
        return comments;
    }

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
            throw new ServiceLayerException();
        }
    }

    @Override
    public boolean update(long id, String newComment) {
        if( id < 1 ){
            logger.error(" There is no such comment for update !");
            throw new InvalidObjectException();
        }
        try{
            commentDAO.update(id,newComment);
            return true;
        }catch(RuntimeException e) {
            logger.error("Failed to update the comment !");
            return false ;
        }
    }

    @Override
    public int delete(long id) {
        if (id < 1) {
            logger.error("Id is invalid");
            throw new InvalidObjectException("Invalid id");
        }
        try {
             commentDAO.delete(id);
             return (int)id;
        } catch(RuntimeException e){
            logger.error("Failed to delete the comment !");
            return -1 ;
        }
    }
}
