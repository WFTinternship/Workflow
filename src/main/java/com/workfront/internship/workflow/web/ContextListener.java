package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.service.AppAreaService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by nane on 6/1/17
 */
public class ContextListener implements ServletContextListener {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
//        AppAreaService appAreaService = BeanProvider.getAppAreaService();
//        AppArea appArea = new AppArea();
//        AppArea[] appAreas = appArea.getAppAreaEnum().values();
//        for (AppArea appArea : appAreas) {
//            if (appAreaService.getById(appArea.getId()) == null) {
//                appAreaService.add(appArea);
//            }
//        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
