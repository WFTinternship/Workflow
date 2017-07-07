package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.UserDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Vahag on 7/6/2017
 */
@Repository
public class UserDAOHibernateImpl extends AbstractDao implements UserDAO{

    private static final Logger LOGGER = Logger.getLogger(UserDAOHibernateImpl.class);

    public UserDAOHibernateImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public long add(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        return user.getId();
    }

    @Override
    public List getByName(String name) {
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
