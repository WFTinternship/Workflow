package com.workfront.internship.workflow.controller;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by nane on 7/15/17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-servlet.xml")
public abstract class BaseControllerTest {

    protected MockMvc mockMvc;

}
