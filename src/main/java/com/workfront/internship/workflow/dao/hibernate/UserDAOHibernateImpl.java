package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Vahag on 7/6/2017
 */

public class UserDAOHibernateImpl extends AbstractDao implements UserDAO {

    private static final Logger LOGGER = Logger.getLogger(UserDAOHibernateImpl.class);

    public UserDAOHibernateImpl(SessionFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * @see UserDAO#add(User)
     */
    @Override
    public long add(User user) {
        long id;
        try {
            Session session = sessionFactory.getCurrentSession();
            id = (long) session.save(user);
        } catch (Exception e) {
            LOGGER.error("Failed to add user");
            throw new DAOException("Failed to add user");
        }
        return id;
    }

    @Override
    public List<User> getByName(String name) {
        return null;
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
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
