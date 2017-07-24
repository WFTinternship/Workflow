package com.workfront.internship.workflow.controller.unit;

import com.workfront.internship.workflow.controller.PostController;
import com.workfront.internship.workflow.entity.Comment;
import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import com.workfront.internship.workflow.service.impl.PostServiceImpl;
import com.workfront.internship.workflow.util.DaoTestUtil;
import com.workfront.internship.workflow.web.PageAttributes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Angel on 7/17/2017
 */
public class PostControllerUnitTest extends BaseUnitTest {

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private PostServiceImpl postServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private CommentService commentServiceMock;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private PostController postController;

    private Post post;
    private User user;
    private List<Post> likedPosts;
    private List<Post> dislikedPosts;
    private List<Comment> postComments;
    private List<Post> answers;

    @Before
    public void init() {
        post = DaoTestUtil.getRandomPost();
        user = DaoTestUtil.getRandomUser();
        post.setUser(user);

        likedPosts = new ArrayList<>();
        dislikedPosts = new ArrayList<>();
        postComments = new ArrayList<>();
        answers = new ArrayList<>();
    }

    /**
     * @see PostController#post(HttpServletRequest)
     */
    @Test
    public void post() throws Exception {
        Long postId = 1L;
        ModelAndView modelAndView = new ModelAndView("post");

        String url = "/post/" + postId;
        doReturn(modelAndView).when(request).getRequestURL();
        doReturn(session).when(request).getSession();

        doReturn(user).when(session).getAttribute(PageAttributes.USER);


        doReturn(likedPosts).when(userServiceMock).getLikedPosts(anyLong());
        doReturn(dislikedPosts).when(userServiceMock).getDislikedPosts(anyLong());
        doReturn(post).when(postServiceMock).getById(anyLong());
        doReturn(postComments).when(commentServiceMock).getByPostId(anyLong());
        doReturn(answers).when(postServiceMock).getAnswersByPostId(anyLong());

        mockMvc.perform(get(url))
                .andExpect(view().name("post"));
//                .andExpect(model().attribute(PageAttributes.POST, post))
//                .andExpect(model().attribute(PageAttributes.POSTCOMMENTS, postComments))
//                .andExpect(model().attribute(PageAttributes.ANSWERS, answers))
//                .andExpect(model().attribute(PageAttributes.LIKEDPOSTS, likedPosts))
//                .andExpect(model().attribute(PageAttributes.DISLIKEDPOSTS, dislikedPosts))
//                .andExpect(status().isOk());
    }

    /**
     * @see PostController#post(HttpServletRequest)
     */
    @Test
    public void postNullUser() throws Exception {
        Long postId = 1L;
        long likesNumber = 9L;

        List<Post> postList = new ArrayList<>();
        postList.add(post);

        List<Post> likedPosts = new ArrayList<>();
        List<Post> dislikedPosts = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/post/" + postId);
        doReturn(stringBuilder).when(request).getRequestURL();
        doReturn(session).when(request).getSession();
        doReturn(null).when(session).getAttribute(PageAttributes.USER);
        doReturn(post).when(postServiceMock).getById(postId);
        doReturn(8L).when(post).getId();
        doReturn(likesNumber).when(postServiceMock).getLikesNumber(anyLong());

//        postController.post(request);

        this.mockMvc.perform(get("/post/" + postId))
                .andExpect(view().name("post"))
                .andExpect(model().attribute(PageAttributes.LIKEDPOSTS, likedPosts))
                .andExpect(model().attribute(PageAttributes.DISLIKEDPOSTS, dislikedPosts));
        assertTrue(userService.getLikedPosts(any(Long.class)).isEmpty());
        assertTrue(userService.getDislikedPosts(any(Long.class)).isEmpty());
    }

    /**
     * @see PostController#newPost()
     */
    @Test
    public void newPost() throws Exception {
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

//      postController.newPost(request);

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
    public void new_post_RunTimeException() {
        doThrow(RuntimeException.class).when(postServiceMock).add(post);

        // Test method
        ModelAndView modelAndView = postController.newPost(request);

        assertEquals(modelAndView.getModel().get(PageAttributes.MESSAGE),
                "Sorry, your post was not added. Please try again");
        assertTrue(modelAndView.getViewName().equals("new_post"));
    }
}
