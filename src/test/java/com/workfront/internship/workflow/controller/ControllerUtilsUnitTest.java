package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.PowerMockUtils;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@PrepareForTest(ControllerUtils.class)
@RunWith(PowerMockRunner.class)
public class ControllerUtilsUnitTest extends BaseUnitTest {
    @Autowired
    private PostService postService;

    @Mock
    private ControllerUtils controllerUtils;

    @Before
    public void init() {
    }
    /*
     public static List<Integer> getNumberOfPostsForAppArea(List<AppArea> appAreas, PostService postService) {
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        try {
            for (AppArea appArea : appAreas) {
                sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
            }
        } catch (ServiceLayerException e) {
            return null;
        }
        return sizeOfPostsBySameAppAreaID;
    }
     */

    /**
     * @see ControllerUtils#getNumberOfPostsForAppArea(List, PostService)
     */
    @Test
    public void getNumberOfPostsForAppArea_success() {
        List<AppArea> appAreas = new ArrayList<>();
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        mockStatic(ControllerUtils.class);
        doReturn(sizeOfPostsBySameAppAreaID)
                .when(ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));

        // Test Method
        List<Integer> expectedList = ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService);
        assertEquals(sizeOfPostsBySameAppAreaID, expectedList);
    }

    /**
     * @see ControllerUtils#getNumberOfPostsForAppArea(List, PostService)
     */
    @Test(expected = RuntimeException.class)
    public void getNumberOfPostsForAppArea_ServiceLayerException() {
        List<AppArea> appAreas = new ArrayList<>();

        mockStatic(ControllerUtils.class);
        doThrow(ServiceLayerException.class)
                .when(ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));

        // Test method
        ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService);
    }
}
