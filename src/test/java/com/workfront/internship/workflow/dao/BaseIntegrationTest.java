package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.entity.AppArea;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * Created by nane on 6/8/17
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-servlet.xml"})
abstract class BaseIntegrationTest {

    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("appAreaDAOSpringImpl")
    AppAreaDAO appAreaDAO;

    Logger LOG;

    @Before
    public void init(){
        AppArea[] appAreas = AppArea.values();
        for (AppArea appArea : appAreas) {
            if (appAreaDAO.getById(appArea.getId()) == null) {
                appAreaDAO.add(appArea);
            }
        }
    }
}
