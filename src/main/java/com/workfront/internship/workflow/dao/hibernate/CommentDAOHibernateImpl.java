package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import javafx.geometry.Pos;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 7/6/2017
 */
@Repository
public class CommentDAOHibernateImpl extends AbstractDao implements CommentDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOHibernateImpl.class);

    /**
     *@see CommentDAO#add(Comment)
     */
    @Override
    public long add(Comment comment) {
        try {
            comment.getPost().getCommentList().add(comment);
            entityManager.persist(comment);
//            entityManager.flush();
        } catch (RuntimeException e) {
            LOGGER.error(" Hibernate Exception ");
            throw new DAOException(e);
        }
        return comment.getId();
    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Override
    public Comment getById(long id) {
       Comment comment;
       try {
           comment = entityManager.find(Comment.class , id);
       } catch(RuntimeException e) {
           LOGGER.error("Hibernate Exception");
           throw new DAOException(e);
       }
       return comment;
    }

    /**
     * @see CommentDAO#getByPostId(long)
     */
    @Override
    public List<Comment> getByPostId(long postId) {
        List<Comment> comments = new ArrayList<>();
        try {
            Post post = entityManager.find(Post.class, postId);
            if(post != null) {
                comments = post.getCommentList();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return comments;
    }

    /**
     * @see CommentDAO#getAll()
     */
    @Override
    public List<Comment> getAll() {
        List<Comment> allComments;
        try {
            allComments = entityManager
                    .createQuery("select c from comment c", Comment.class)
                    .getResultList();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return allComments;
    }

    /**
     * @see CommentDAO#update(Comment)
     */
    @Override
    public void update(Comment comment) {
        try {
            entityManager.merge(comment);
            entityManager.flush();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Override
    public void delete(long id) {
        try {
            Comment comment = entityManager.find(Comment.class, id);
            if(comment != null) {
                entityManager.remove(comment);
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }
}
