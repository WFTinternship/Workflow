package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created by Vahag on 7/6/2017
 */
@Repository
public class UserDAOHibernateImpl extends AbstractDao implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOHibernateImpl.class);

    @Override
    public long add(User user) {
        try {
            entityManager.persist(user);
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return user.getId();
    }

    @Override
    public List<User> getByName(String name) {
        String filteredName = name.replaceAll(" ", "");
        List<User> users;
        try {
            users = entityManager.createQuery(
                    "SELECT c FROM user c WHERE concat(c.firstName, c.lastName) LIKE :name")
                    .setParameter("name", '%' + filteredName + '%')
                    .getResultList();
        } catch (RuntimeException e) {
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
        } catch (RuntimeException e) {
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
        } catch (NoResultException e) {
            return null;
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public List<AppArea> getAppAreasById(long id) {
        List<AppArea> appAreas;
        try {
            User user = (User) entityManager.createQuery(
                    "SELECT c FROM user c WHERE c.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
            appAreas = user.getAppAreas();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return appAreas;
    }

    @Override
    public void subscribeToArea(long userId, long appAreaId) {
        try {
            User user = entityManager.find(User.class, userId);

            user.getAppAreas().add(AppArea.getById(appAreaId));

            entityManager.merge(user);
            entityManager.flush();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    @Override
    public void unsubscribeToArea(long userId, long appAreaId) {
        try {
            User user = entityManager.find(User.class, userId);

            user.getAppAreas().remove(AppArea.getById(appAreaId));

            entityManager.merge(user);
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            User user = entityManager.find(User.class, id);

            if (user != null) {
                entityManager.remove(user);
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void updateProfile(User user) {
        try {
            entityManager.merge(user);
            entityManager.flush();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }

    @Override
    public void updateAvatar(User user) {

    }
}
