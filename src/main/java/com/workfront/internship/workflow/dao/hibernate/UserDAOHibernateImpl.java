package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Vahag on 7/6/2017
 */
//@Repository
public class UserDAOHibernateImpl extends AbstractDao implements UserDAO{

    private static final Logger LOGGER = Logger.getLogger(UserDAOHibernateImpl.class);

    public UserDAOHibernateImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public long add(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return user.getId();
    }

    @Override
    public List getByName(String name) {
        /*EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<User> users;
        try {
            users = entityManager.createQuery(
                    "SELECT c FROM user c WHERE c.name LIKE :name")
                    .setParameter("name", name)
                    .setMaxResults(10)
                    .getResultList();
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return users;*/
        return null;
    }

    @Override
    public User getById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user;
        try {
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        /*EntityManager entityManager = entityManagerFactory.createEntityManager();
        User user;
        try {
            user = (User) entityManager.createQuery(
                    "SELECT c FROM user c WHERE c.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return user;*/
        return null;
    }

    @Override
    public List<AppArea> getAppAreasById(long id) {
        return null;
    }

    @Override
    public void subscribeToArea(long userId, long appAreaId) {



    }

    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {

    }

    @Override
    public void deleteById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            User user = entityManager.find(User.class, id);

            if (user != null){
                entityManager.getTransaction().begin();
                entityManager.remove(user);
                entityManager.getTransaction().commit();
            }
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void updateProfile(User user) {

    }

    @Override
    public void updateAvatar(User user) {

    }
}
