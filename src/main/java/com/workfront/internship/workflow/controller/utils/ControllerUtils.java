package com.workfront.internship.workflow.controller.utils;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.service.PostService;
import com.workfront.internship.workflow.web.PageAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nane on 7/1/17
 */
public class ControllerUtils {

    private static List<AppArea> appAreas = new ArrayList<>(Arrays.asList(AppArea.values()));

    public static List<Integer> getNumberOfPostsForAppArea(List<AppArea> appAreas, PostService postService) {
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        for (AppArea appArea : appAreas) {
            sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
        }
        return sizeOfPostsBySameAppAreaID;
    }

    public static List<Integer> getNumberOfAnswers(List<Post> postList, PostService postService) {
        List<Integer> numbersOfAnswersForPosts = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        for (Post post : postList) {
            numbersOfAnswersForPosts.add(postService.getNumberOfAnswers(post.getId()));
        }
        return numbersOfAnswersForPosts;
    }

//    public static void setAppAreas(ModelAndView modelAndView) {
//        modelAndView.addObject(PageAttributes.APPAREAS, appAreas);
//    }
}
