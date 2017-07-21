package com.workfront.internship.workflow.service.unit;

import com.workfront.internship.workflow.service.BaseTest;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * Created by nane on 6/21/17
 */
public abstract class BaseUnitTest extends BaseTest {

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

}
