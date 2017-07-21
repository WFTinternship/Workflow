package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@PrepareForTest(ControllerUtils.class)
@RunWith(PowerMockRunner.class)
public class ControllerUtilsUnitTest extends BaseUnitTest {
    @Autowired
    private PostService postService;


    @Mock
    private PostService postServiceMock;

    @InjectMocks
    private ControllerUtils controllerUtils;


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @see ControllerUtils#getNumberOfPostsForAppArea(List, PostService)
     */
    @Test
    public void getNumberOfPostsForAppArea_success() {
        List<Post> posts = new ArrayList<>();
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        List<AppArea> appAreas = new ArrayList<>();
        Long appAreaId = 7L;
        appAreas = AppArea.getAsList();

        mockStatic(ControllerUtils.class);
        doReturn(posts).when(postServiceMock).getByAppAreaId(appAreaId);

        // Test Method
        List<Integer> expectedList = ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService);
        assertEquals(sizeOfPostsBySameAppAreaID, expectedList);
    }

    /**
     * @see ControllerUtils#getNumberOfPostsForAppArea(List, PostService)
     */
    @Test
    public void getNumberOfPostsForAppArea_ServiceLayerException() {
        List<AppArea> appAreas = new ArrayList<>();
        Long appAreaId = 7L;
        doThrow(ServiceLayerException.class)
                .when(postServiceMock).getByAppAreaId(appAreaId);

        // Test method
        List<Integer> expectedList = ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService);
        assertTrue(expectedList.isEmpty());
    }

    /**
     * @see ControllerUtils#getNumberOfAnswers(List, PostService)
     */
    @Test
    public void getNumberOfAnswers_success() {

    }

    /**
     * @see ControllerUtils#getNumberOfAnswers(List, PostService)
     */
    @Test
    public void getNumberOfAnswers_ServiceLayerException() {
        List<Post> postList = new ArrayList<>();
        Long postId = 7L;
        doThrow(ServiceLayerException.class).when(postServiceMock).getNumberOfAnswers(postId);

        // Test method
        List<Integer> expectedList = ControllerUtils.getNumberOfAnswers(postList, postService);
        assertTrue(expectedList.isEmpty());
    }

    /**
     * @see ControllerUtils#getNumberOfLikes(List, PostService)
     */
    @Test
    public void getNumberOfLikes_success() {

    }

    /**
     * @see ControllerUtils#getNumberOfLikes(List, PostService)
     */
    @Test
    public void getNumberOfLikes_ServiceLayerException() {
        List<Post> postList = new ArrayList<>();
        Long postId = 7L;
        doThrow(ServiceLayerException.class).when(postServiceMock).getLikesNumber(postId);

        // Test method
        List<Long> expectedList = ControllerUtils.getNumberOfLikes(postList, postService);
        assertTrue(expectedList.isEmpty());
    }

    /**
     * @see ControllerUtils#getNumberOfDislikes(List, PostService)
     */
    @Test
    public void getNumberOfDislikes_success() {

    }

    /**
     * @see ControllerUtils#getNumberOfDislikes(List, PostService)
     */
    @Test
    public void getNumberOfDislikes_ServiceLayerException() {
        List<Post> postList = new ArrayList<>();
        Long postId = 7L;
        doThrow(ServiceLayerException.class).when(postServiceMock).getDislikesNumber(postId);

        // Test method
        List<Long> expectedList = ControllerUtils.getNumberOfLikes(postList, postService);
        assertTrue(expectedList.isEmpty());
    }


    /**
     * @see ControllerUtils#getDifOfLikesDislikes(List, PostService)
     */
    @Test
    public void getDifOfLikesDislikes_success() {

    }

    /**
     * @see ControllerUtils#getDifOfLikesDislikes(List, PostService)
     */
    @Test
    public void getDifOfLikesDislikes_ServiceLayerException() {
        List<Post> postList = new ArrayList<>();
        Long postId = 7L;
        doThrow(ServiceLayerException.class).when(postServiceMock).getLikesNumber(postId);
        doThrow(ServiceLayerException.class).when(postServiceMock).getDislikesNumber(postId);

        // Test method
        List<Long> expectedList = ControllerUtils.getNumberOfLikes(postList, postService);
        assertTrue(expectedList.isEmpty());
    }

    /**
     * @see ControllerUtils#getMostDiscussedPosts(PostService, List)
     */
    @Test
    public void getMostDiscussedPosts_success() {

    }

    /**
     * @see ControllerUtils#getMostDiscussedPosts(PostService, List)
     */
    @Test
    public void getMostDiscussedPosts_ServiceLayerException() {
        List<Post> posts = new ArrayList<>();
        Long postId = 7L;
        doThrow(ServiceLayerException.class).when(postServiceMock).getAnswersByPostId(postId);

        // Test method
        List<Post> expectedList = ControllerUtils.getMostDiscussedPosts(postService, posts);
        assertTrue(expectedList.isEmpty());
    }

    /**
     * @see ControllerUtils#getTopPosts(PostService, List)
     */
    @Test
    public void getTopPosts_success() {

    }

    /**
     * @see ControllerUtils#getTopPosts(PostService, List)
     */
    @Test
    public void getTopPosts_ServiceLayerException() {
        List<Post> posts = new ArrayList<>();
        Long postId = 7L;
        doThrow(ServiceLayerException.class).when(postServiceMock).getDislikesNumber(postId);
        doThrow(ServiceLayerException.class).when(postServiceMock).getLikesNumber(postId);

        // Test method
        List<Post> expectedList = ControllerUtils.getTopPosts(postService, posts);
        assertTrue(expectedList.isEmpty());
    }


    /**
     * @see ControllerUtils#setDefaultAttributes(PostService, ModelAndView)
     */
    @Test
    public void setDefaultAttributes_success() {
        List<Post> allPosts = postService.getAll();
        ModelAndView modelAndView = new ModelAndView();
    }

    @Test
    public void setDefaultAttributes_gettingPostsList_success() {
        /*ModelAndView modelAndView = new ModelAndView();
        List<Post> posts = new ArrayList<>();
        List<AppArea> appAreas = new ArrayList<>();
        mockStatic(ControllerUtils.class);
        List<Post> topPosts = new ArrayList<>();
        List<Post> mostDiscussedPosts = new ArrayList<>();
       // doReturn(topPosts).when(ControllerUtils.getTopPosts(postService, posts));
       // doReturn(mostDiscussedPosts).when(ControllerUtils.getMostDiscussedPosts(postService, posts));

        doReturn(PageAttributes.NUMOFANSWERS).when(ControllerUtils.getNumberOfAnswers(posts, postService));
        doReturn(PageAttributes.NUMOFANSWERSFORMDP).when(ControllerUtils.getNumberOfAnswers(mostDiscussedPosts, postService));
        doReturn(PageAttributes.DIFOFLIKESDISLIKES).when(ControllerUtils.getDifOfLikesDislikes(topPosts, postService));
        doReturn(PageAttributes.POSTS_OF_APPAAREA).when(ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));

        // Test method
        ControllerUtils.setDefaultAttributes(postService, posts, modelAndView);

        assertEquals(modelAndView.getModel().get(PageAttributes.NUMOFANSWERS),ControllerUtils.getNumberOfAnswers(posts, postService));
        assertEquals(modelAndView.getModel().get(PageAttributes.NUMOFANSWERSFORMDP),ControllerUtils.getNumberOfAnswers(mostDiscussedPosts, postService));
        assertEquals(modelAndView.getModel().get(PageAttributes.DIFOFLIKESDISLIKES),ControllerUtils.getDifOfLikesDislikes(topPosts, postService));
        assertEquals(modelAndView.getModel().get(PageAttributes.APPAREAS),appAreas);
        assertEquals(modelAndView.getModel().get(PageAttributes.POSTS_OF_APPAAREA),ControllerUtils.getNumberOfPostsForAppArea(appAreas, postService));
        assertEquals(modelAndView.getModel().get(PageAttributes.TOPPOSTS),topPosts);
        assertEquals(modelAndView.getModel().get(PageAttributes.MOSTDISCUSSEDPOSTS),mostDiscussedPosts);
*/


    }

}
