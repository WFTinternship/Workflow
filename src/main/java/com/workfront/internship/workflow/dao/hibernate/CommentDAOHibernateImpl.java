package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.CommentDAO;
import com.workfront.internship.workflow.entity.Comment;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Angel on 7/6/2017
 */
public class CommentDAOHibernateImpl extends AbstractDao implements CommentDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOHibernateImpl.class);

    public CommentDAOHibernateImpl(SessionFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     *@see CommentDAO#add(Comment)
     */
    @Override
    public long add(Comment comment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        long id = 0;

        try {
            entityManager.persist(comment);
        } catch (HibernateException e) {
            LOGGER.error("Hibernate exception");
            throw new RuntimeException();
        }

        return id;
    }

    /**
     * @see CommentDAO#getById(long)
     */
    @Override
    public Comment getById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
             entityManager.persist(id);
        } catch (HibernateException e){
            LOGGER.error("Hibernate exception");
            throw new RuntimeException();
        }
        return null;
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

    }
}
