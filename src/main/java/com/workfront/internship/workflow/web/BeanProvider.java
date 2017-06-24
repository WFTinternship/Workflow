package com.workfront.internship.workflow.web;

import com.workfront.internship.workflow.service.AppAreaService;
import com.workfront.internship.workflow.service.CommentService;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.service.UserService;
import org.springframework.context.ApplicationContext;

/**
 * Created by nane on 6/20/17
 */
public class BeanProvider {

    public static UserService getUserService() {
        return (UserService) ContextListener.getApplicationContext().getBean("userService");
    }

    public static PostService getPostService() {
        return (PostService) ContextListener.getApplicationContext().getBean("postService");
    }

    public static CommentService getCommentService() {
        return (CommentService) ContextListener.getApplicationContext().getBean("commentService");
    }

    public static AppAreaService getAppAreaService(){
        return (AppAreaService) ContextListener.getApplicationContext().getBean("appAreaService");
    }
}
