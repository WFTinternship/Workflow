package com.workfront.internship.workflow.controller.unit;

import com.workfront.internship.workflow.controller.BaseControllerTest;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Angel on 7/13/2017
 */
public class BaseUnitTest extends BaseControllerTest {

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
}
