package com.workfront.internship.workflow.controller.integration;

import com.workfront.internship.workflow.controller.BaseControllerTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by nane on 7/24/17
 */
@WebAppConfiguration
public abstract class BaseIntegrationTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();
    }
}
