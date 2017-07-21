package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.controller.utils.ControllerUtils;

import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import javafx.geometry.Pos;
import org.omg.SendingContext.RunTime;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import org.springframework.web.context.WebApplicationContext;
import com.workfront.internship.workflow.web.PageAttributes;
import com.workfront.internship.workflow.util.DaoTestUtil;
import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.InjectMocks;
import org.junit.Before;
import org.mockito.Mock;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Angel on 7/17/2017
 */
public class PostControllerUnitTest extends BaseUnitTest {
    @Autowired
    private PostService postService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private CommentService commentServiceMock;

    @Mock
    private PostService postServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private HttpSession session ;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    @Autowired
    private PostController postController;

    @InjectMocks
    private MockMvc mockMvc;

    private Post post;
    private User user;
    private AppArea appArea;
    private List<Post> likedPosts ;
    private List<Post> dislikedPosts ;
    private List<Comment> postComments ;
    private List<Post> answers;
    private List<Post> allPosts;
//  private List<AppArea> appAreas;
//  private List<Long> numOfLikes;
//  private List<Long> numOfDisLikes;

    @Before
    public void init() {
        post = DaoTestUtil.getRandomPost();
        user = DaoTestUtil.getRandomUser();
        appArea = DaoTestUtil.getRandomAppArea();
        post.setUser(user);
        post.setAppArea(appArea);

        likedPosts = new ArrayList<>();
//      appAreas = new ArrayList<>();
        allPosts = new ArrayList<>();
        dislikedPosts = new ArrayList<>();
        postComments = new ArrayList<>();
        answers = new ArrayList<>();
//      numOfLikes = ControllerUtils.getNumberOfLikes(allPosts, postService);
//      numOfDisLikes = ControllerUtils.getNumberOfDislikes(allPosts, postService);

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();
        MockitoAnnotations.initMocks(this);
    }

    /**
     * @see PostController#post(HttpServletRequest)
     * @throws Exception
     */
    @Test
    public void post() throws Exception {
        Long postId = post.getId();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/post/" + postId);
        doReturn(stringBuffer).when(request).getRequestURL();
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute(PageAttributes.USER);
        doReturn(likedPosts).when(userServiceMock).getLikedPosts(user.getId());
        doReturn(dislikedPosts).when(userServiceMock).getDislikedPosts(user.getId());
        doReturn(post).when(postServiceMock).getById(post.getId());
        doReturn(postComments).when(commentServiceMock).getByPostId(post.getId());
        doReturn(answers).when(postServiceMock).getAnswersByPostId(post.getId());

        // Test method
        postController.post(request);

        this.mockMvc.perform(get("/post/" + postId))
                .andExpect(view().name("post"))
                .andExpect(model().attribute(PageAttributes.POST, post))
                .andExpect(model().attribute(PageAttributes.POSTCOMMENTS, postComments))
                .andExpect(model().attribute(PageAttributes.ANSWERS, answers))
                .andExpect(model().attribute(PageAttributes.LIKEDPOSTS, likedPosts))
                .andExpect(model().attribute(PageAttributes.DISLIKEDPOSTS, dislikedPosts))
                .andExpect(status().isOk());
    }

    /**
     * @see PostController#newPost()
     */
    @Test
    public void new_post() throws Exception {
        Long postId = 7L;
        String appAreaIdAsString = "7";
        String title = "title";
        String content = "content";
        String notify = "note";

        doReturn(title).when(request).getParameter(PageAttributes.TITLE);
        doReturn(content).when(request).getParameter(PageAttributes.POSTCONTENT);
        doReturn(notify).when(request).getParameter(PageAttributes.NOTE);
        doReturn(appAreaIdAsString).when(request).getParameter(PageAttributes.APPAREA);
        doReturn(session).when(request).getSession();
        doReturn(user).when(session).getAttribute(PageAttributes.USER);
        doReturn(postId).when(postServiceMock).add(post);

        // Test method
        postController.newPost(request);

        List<Post> allPosts = postService.getAll();

        this.mockMvc.perform(get("/new-post"))
                .andExpect(view().name("new_post"))
                .andExpect(model().attribute(PageAttributes.ALLPOSTS, allPosts))
                .andExpect(status().isOk());
    }
    /**
     * @see PostController#newPost()
     */
    @Test(expected = RuntimeException.class)
    public void new_post_RunTimeException()  {
        doThrow(RuntimeException.class).when(postServiceMock).add(post);

        // Test method
        ModelAndView modelAndView = postController.newPost(request);

        assertEquals(modelAndView.getModel().get(PageAttributes.MESSAGE),
                "Sorry, your post was not added. Please try again" );
        assertTrue(modelAndView.getViewName().equals("new_post"));
    }
}
