package com.workfront.internship.workflow.dao.hibernate;

import com.workfront.internship.workflow.dao.AbstractDao;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

/**
 * Created by Vahag on 7/6/2017
 */

public class UserDAOHibernateImpl extends AbstractDao {

    private static final Logger LOGGER = Logger.getLogger(UserDAOHibernateImpl.class);

    public UserDAOHibernateImpl(SessionFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


}
