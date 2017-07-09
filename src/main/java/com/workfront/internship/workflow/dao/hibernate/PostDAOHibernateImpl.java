package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import javafx.geometry.Pos;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by nane on 7/5/17
 */

@Repository
public class PostDAOHibernateImpl extends AbstractDao implements PostDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOHibernateImpl.class);

    /**
     * @see PostDAO#add(Post)
     */
    @Override
    public long add(Post post) {
       try {
            entityManager.getTransaction().begin();
            entityManager.persist(post);
            entityManager.getTransaction().commit();

            entityManager.flush();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return post.getId();
    }

    /**
     * @see PostDAO#getAll()
     */
    @Override
    public List<Post> getAll() {
       List<Post> allPosts = entityManager
                .createQuery("select a from post", Post.class)
                .getResultList();
        return allPosts;
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
       Post post;
        try {
            entityManager.getTransaction().begin();
            post = entityManager.find(Post.class, id);
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return post;
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
       long count;
        try {
            count = (long) entityManager
                    .createQuery("select COUNT(d.user_id) " +
                            "from user_post_likes d " +
                            "where d.post_id = :postId")
                    .setParameter("postId", postId)
                    .getSingleResult();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return count;
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
        try {
            // TODO: need to be discussed
            Post post = getById(postId);
            Post answer = getById(answerId);
            post.setBestAnswer(answer);

            entityManager.persist(post);
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
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
       try {
            entityManager
                    .createNativeQuery("insert into user_post_likes (user_id, post_id) " +
                            "VALUES (?, ?)")
                    .setParameter(1, userId)
                    .setParameter(2, postId)
                    .executeUpdate();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
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
        try {
            Post post = entityManager.find(Post.class, id);

            entityManager.getTransaction().begin();
            entityManager.remove(post);
            entityManager.getTransaction().commit();

        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
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
