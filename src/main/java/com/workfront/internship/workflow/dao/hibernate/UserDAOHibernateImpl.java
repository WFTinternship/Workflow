package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Vahag on 7/6/2017
 */
@Repository
public class UserDAOHibernateImpl extends AbstractDao implements UserDAO{

    private static final Logger LOGGER = Logger.getLogger(UserDAOHibernateImpl.class);

    @Transactional
    @Override
    public long add(User user) {
       try {
            entityManager.persist(user);
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return user.getId();
    }

    @Override
    public List getByName(String name) {
       List<User> users;
        try {
            users = entityManager.createQuery(
                    "SELECT c FROM user c WHERE c.name LIKE :name")
                    .setParameter("name", name)
                    .getResultList();
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public User getById(long id) {
        User user;
        try {
            user = entityManager.find(User.class, id);
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
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
        return user;
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

    @Transactional
    @Override
    public void deleteById(long id) {
       try {
            User user = entityManager.find(User.class, id);

            if (user != null){
                entityManager.remove(user);
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
