package com.workfront.internship.workflow.controller.utils;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.service.PostService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nane on 7/1/17
 */
public class ControllerUtils {

    public static List<Integer> getNumberOfPostsForAppArea(List<AppArea> appAreas, PostService postService) {
        List<Integer> sizeOfPostsBySameAppAreaID = new ArrayList<>();
        // getting and passing list of sizes of each posts by same appArea id to home page
        for (AppArea appArea : appAreas) {
            sizeOfPostsBySameAppAreaID.add(postService.getByAppAreaId(appArea.getId()).size());
        }
        return sizeOfPostsBySameAppAreaID;
    }
}
