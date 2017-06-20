package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.workflow.domain.AppArea;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by nane on 6/1/17
 */
public class ContextListener implements ServletContextListener {

    private static ApplicationContext applicationContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        AppAreaDAO appAreaDAO = new AppAreaDAOImpl();
        AppArea[] appAreas = AppArea.values();
        for (AppArea appArea : appAreas) {
            if (appAreaDAO.getById(appArea.getId()) == null) {
                appAreaDAO.add(appArea);
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
