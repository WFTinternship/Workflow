package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.PostDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
            if (post.getPost() == null) {
                post.getUser().getPosts().add(post);
            } else {
                post.getPost().getAnswerList().add(post);
            }
            entityManager.persist(post);
//            entityManager.flush();
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
        List<Post> allPosts;
        try {
            allPosts = entityManager
                    .createQuery("select p from post p", Post.class)
                    .getResultList();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return allPosts;
    }

    /**
     * @see PostDAO#getByUserId(long)
     */
    @Override
    public List<Post> getByUserId(long userId) {
        List<Post> userPosts = new ArrayList<>();
        try {
            User user = entityManager.find(User.class, userId);
            if (user != null) {
                userPosts = user.getPosts();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return userPosts;
    }

    /**
     * @see PostDAO#getByAppAreaId(long)
     */
    @Override
    public List<Post> getByAppAreaId(long id) {
        List<Post> posts = new ArrayList<>();
        try {
            AppArea appArea = AppArea.getById(id);
            if (appArea != null) {
                String name = appArea.name();
                posts = entityManager
                        .createNativeQuery("SELECT * FROM post where post.appArea" +
                                " =:id", Post.class)
                        .setParameter("id", name)
                        .getResultList();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return posts;
    }

    /**
     * @see PostDAO#getByTitle(String)
     */
    @Override
    public List<Post> getByTitle(String title) {
        List<Post> posts;
        try {
            posts = entityManager
                    .createQuery("select p from post p where p.title like :title", Post.class)
                    .setParameter("title", '%' + title + '%')
                    .getResultList();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return posts;
    }

    /**
     * @see PostDAO#getById(long)
     */
    @Override
    public Post getById(long id) {
        Post post;
        try {
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
        List<Post> answers;
        try {
            Post post = entityManager.find(Post.class, postId);
            answers = post.getAnswerList();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return answers;
    }

    /**
     * @see PostDAO#getBestAnswer(long)
     */
    @Override
    public Post getBestAnswer(long postId) {
        Post answer;
        try {
            Post post = entityManager.find(Post.class, postId);
            answer = post.getBestAnswer();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return answer;
    }

    /**
     * @see PostDAO#getLikesNumber(long)
     */
    @Override
    public long getLikesNumber(long postId) {
        long numOfLikes = 0;
        try {
            Post post = entityManager.find(Post.class, postId);
            if (post != null) {
                numOfLikes = post.getLikers().size();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return numOfLikes;
    }

    /**
     * @see PostDAO#getDislikesNumber(long)
     */
    @Override
    public long getDislikesNumber(long postId) {
        long count = 0;
        try {
            Post post = entityManager.find(Post.class, postId);
            if (post != null) {
                count = post.getDislikers().size();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return count;
    }

    /**
     * @see PostDAO#setBestAnswer(long, long)
     */
    @Override
    public void setBestAnswer(long postId, long answerId) {
        try {
            Post post = entityManager.find(Post.class, postId);
            Post answer = entityManager.find(Post.class, answerId);
            post.setBestAnswer(answer);

            entityManager.merge(post);
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
        try {
            entityManager.merge(post);
            entityManager.flush();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#like(long, long)
     */
    @Override
    public void like(long userId, long postId) {
        try {
            User user = entityManager.find(User.class, userId);
            Post post = entityManager.find(Post.class, postId);
            if (post != null && user != null) {
                post.getLikers().add(user);
                user.getLikedPosts().add(post);
                entityManager.merge(post);
                entityManager.flush();
            }
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
        try {
            User user = entityManager.find(User.class, userId);
            Post post = entityManager.find(Post.class, postId);
            if (post != null && user != null) {
                post.getDislikers().add(user);
                user.getDislikedPosts().add(post);
                entityManager.merge(post);
                entityManager.flush();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#delete(long)
     */
    @Override
    public void delete(long id) {
        try {
            Post post = entityManager.find(Post.class, id);
            if (post != null) {
                entityManager.remove(post);
            }
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
        int numOfAnswers = 0;
        try {
            Post post = entityManager.find(Post.class, postId);
            if (post != null) {
                numOfAnswers = post.getAnswerList().size();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return numOfAnswers;
    }

    /**
     * @see PostDAO#getNotified(long, long)
     */
    @Override
    public void getNotified(long postId, long userId) {
        try {
            Post post = entityManager.find(Post.class, postId);
            User user = entityManager.find(User.class, userId);
            post.addNotificationRecipient(user);
            entityManager.merge(post);

        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    /**
     * @see PostDAO#getNotificationRecipients(long)
     */
    @Override
    public List<User> getNotificationRecipients(long postId) {
        List<User> recipients = new ArrayList<>();
        try {
            Post post = entityManager.find(Post.class, postId);
            if (post != null) {
                recipients = post.getNotificationRecepiants();
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return recipients;
    }
}
