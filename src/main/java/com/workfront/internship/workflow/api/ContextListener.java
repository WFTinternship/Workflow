package com.workfront.internship.workflow.api;

import com.workfront.internship.workflow.dao.AppAreaDAO;
import com.workfront.internship.workflow.dao.impl.AppAreaDAOImpl;
import com.workfront.internship.workflow.dataModel.AppArea;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by nane on 6/1/17.
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        AppAreaDAO appAreaDAO = new AppAreaDAOImpl();
        AppArea[] appAreas = AppArea.values();
        for (AppArea appArea: appAreas) {
            if(appAreaDAO.getById(appArea.getId()) == null){
                appAreaDAO.add(appArea);
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
