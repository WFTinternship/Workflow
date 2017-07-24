package com.workfront.internship.workflow.controller.unit;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import com.workfront.internship.workflow.service.PostService;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ControllerUtilsUnitTest extends BaseUnitTest {

    @Autowired
    private PostService postService;

    @Mock
    private PostService postServiceMock;

    /**
     * @see ControllerUtils#getNumberOfPostsForAppArea(List, PostService)
     */
    @Test
    public void getNumberOfPostsForAppArea_success() {
        List<Post> posts = new ArrayList<>();
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        List<AppArea> appAreas = new ArrayList<>();
        Long appAreaId = 7L;

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
    public void getTopPosts_ServiceLayerException() {
        List<Post> posts = new ArrayList<>();
        Long postId = 7L;
        doThrow(ServiceLayerException.class).when(postServiceMock).getDislikesNumber(postId);
        doThrow(ServiceLayerException.class).when(postServiceMock).getLikesNumber(postId);

        // Test method
        List<Post> expectedList = ControllerUtils.getTopPosts(postService, posts);
        assertTrue(expectedList.isEmpty());
    }
}
