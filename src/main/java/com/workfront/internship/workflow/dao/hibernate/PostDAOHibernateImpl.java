package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by nane on 7/5/17
 */
public class PostDAOHibernateImpl extends AbstractDao implements PostDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOHibernateImpl.class);

    public PostDAOHibernateImpl(SessionFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @see PostDAO#add(Post)
     */
    @Override
    public long add(Post post) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        long id = 0;
        try {
            entityManager.persist(post);
        } catch (HibernateException e) {
            LOGGER.error("Hibernate Exception");
            throw new RuntimeException(e);
        }
        return id;
    }

    /**
     * @see PostDAO#getAll()
     */
    @Override
    public List<Post> getAll() {
        return null;
    }

    /**
     * @see PostDAO#getByUserId(long)
     */
    @Override
    public List<Post> getByUserId(long userId) {
        return null;
    }

    /**
     * @see PostDAO#getByAppAreaId(long)
     */
    @Override
    public List<Post> getByAppAreaId(long id) {
        return null;
    }

    /**
     * @see PostDAO#getByTitle(String)
     */
    @Override
    public List<Post> getByTitle(String title) {
        return null;
    }

    /**
     * @see PostDAO#getById(long)
     */
    @Override
    public Post getById(long id) {
        return null;
    }

    /**
     * @see PostDAO#getAnswersByPostId(long)
     */
    @Override
    public List<Post> getAnswersByPostId(long postId) {
        return null;
    }

    /**
     * @see PostDAO#getBestAnswer(long)
     */
    @Override
    public Post getBestAnswer(long postId) {
        return null;
    }

    /**
     * @see PostDAO#getLikesNumber(long)
     */
    @Override
    public long getLikesNumber(long postId) {
        return 0;
    }

    /**
     * @see PostDAO#getDislikesNumber(long)
     */
    @Override
    public long getDislikesNumber(long postId) {
        return 0;
    }

    /**
     * @see PostDAO#setBestAnswer(long, long)
     */
    @Override
    public void setBestAnswer(long postId, long answerId) {

    }

    /**
     * @see PostDAO#update(Post)
     */
    @Override
    public void update(Post post) {

    }

    /**
     * @see PostDAO#like(long, long)
     */
    @Override
    public void like(long userId, long postId) {

    }

    /**
     * @see PostDAO#dislike(long, long)
     */
    @Override
    public void dislike(long userId, long postId) {

    }

    /**
     * @see PostDAO#delete(long)
     */
    @Override
    public void delete(long id) {

    }

    /**
     * @see PostDAO#getNumberOfAnswers(long)
     */
    @Override
    public Integer getNumberOfAnswers(long postId) {
        return null;
    }

    /**
     * @see PostDAO#getNotified(long, long)
     */
    @Override
    public void getNotified(long postId, long userId) {

    }

    /**
     * @see PostDAO#getNotificationRecipients(long)
     */
    @Override
    public List<User> getNotificationRecipients(long postId) {
        return null;
    }
}
