package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;


import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Angel on 7/6/2017
 */
public class CommentDAOHibernateImpl extends AbstractDao implements CommentDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOHibernateImpl.class);

    /**
     *@see CommentDAO#add(Comment)
     */
    @Override
    public long add(Comment comment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Comment comment;
        try {
            entityManager.getTransaction().begin();
            comment = entityManager.find(Comment.class, id);
        } catch (RuntimeException e) {
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

      return null;
    }

    /**
     * @see CommentDAO#getAll()
     */
    @Override
    public List<Comment> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager
                .createQuery("select a from comment", Comment.class)
                //.createQuery("select a from comment", Comment.class)
                .getResultList();
        return null;
    }

    /**
     * @see CommentDAO#update(long, String)
     */
    @Override
    public boolean update(long id, String newComment) {
        return false;
    }

    /**
     * @see CommentDAO#delete(long)
     */
    @Override
    public void delete(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Comment comment = entityManager.find(Comment.class, id);

            entityManager.getTransaction().begin();
            entityManager.remove(comment);
            entityManager.getTransaction().commit();

        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }

    }
}
