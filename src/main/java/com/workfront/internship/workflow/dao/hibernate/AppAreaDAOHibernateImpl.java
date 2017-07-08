package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Vahag on 7/8/2017
 */
@Repository
public class AppAreaDAOHibernateImpl extends AbstractDao implements AppAreaDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOHibernateImpl.class);

    public AppAreaDAOHibernateImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public long add(AppArea appArea) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(appArea);
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return appArea.getAppAreaEnum().getId();
    }

    @Override
    public List<User> getUsersById(long appAreaId) {
        return null;
    }

    @Override
    public AppArea getById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        AppArea appArea;
        try {
            entityManager.getTransaction().begin();
            appArea = entityManager.find(AppArea.class, id);
        }catch (RuntimeException e){
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return appArea;
    }

    @Override
    public void deleteById(long id) {

    }
}
