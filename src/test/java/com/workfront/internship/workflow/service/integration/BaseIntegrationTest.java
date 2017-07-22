package com.workfront.internship.workflow.service.integration;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.service.AppAreaService;
import com.workfront.internship.workflow.service.BaseTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Vahag on 6/22/2017
 */
public abstract class BaseIntegrationTest extends BaseTest {

    @Autowired
    AppAreaService appAreaService;

    @Before
    public void init(){
        AppArea[] appAreas = AppArea.values();
        for (AppArea appArea : appAreas) {
            if (appAreaService.getById(appArea.getId()) == null) {
                appAreaService.add(appArea);
            }
        }
    }
}
