package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.entity.AppArea;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * Created by Vahag on 6/22/2017
 */
@Transactional
public abstract class BaseIntegrationTest extends BaseTest{

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
