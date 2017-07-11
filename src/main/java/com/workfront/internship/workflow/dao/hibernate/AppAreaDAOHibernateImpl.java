package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.exceptions.dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vahag on 7/8/2017
 */

@Repository
public class AppAreaDAOHibernateImpl extends AbstractDao implements AppAreaDAO {

    private static final Logger LOGGER = Logger.getLogger(PostDAOHibernateImpl.class);

    @Override
    public long add(AppArea appArea) {
        try {
            entityManager
                    .createNativeQuery("INSERT INTO apparea (id, name," +
                            " description, team_name) " +
                            "VALUES (?, ?, ?, ?)")
                    .setParameter(1, appArea.getId())
                    .setParameter(2, appArea.getName())
                    .setParameter(3, appArea.getDescription())
                    .setParameter(4, appArea.getTeamName())
                    .executeUpdate();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return appArea.getId();
    }

    @Override
    public List<User> getUsersById(long appAreaId) {
      return null;
    }

    @Override
    public AppArea getById(long id) {
        AppArea appArea = null;
        try {
            long count = ((Number) this.entityManager
                    .createNativeQuery("SELECT COUNT(*) FROM apparea " +
                            "WHERE id = ?")
                    .setParameter(1, id)
                    .getResultList().get(0))
                    .longValue();
            if (count > 0) {
                appArea = AppArea.getById(id);
            }
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
        return appArea;
    }

    @Override
    public void deleteById(long id) {
        try {
            entityManager.createNativeQuery("DELETE FROM apparea " +
                    "WHERE id = ?")
                    .setParameter(1, id)
                    .executeUpdate();
        } catch (RuntimeException e) {
            LOGGER.error("Hibernate Exception");
            throw new DAOException(e);
        }
    }
}
