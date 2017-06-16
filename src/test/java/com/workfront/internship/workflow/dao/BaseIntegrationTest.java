package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.util.DBHelper;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * Created by nane on 6/8/17
 */
abstract class BaseIntegrationTest {
    protected DataSource dataSource = DBHelper.getPooledConnection();
    Logger LOG;
}
