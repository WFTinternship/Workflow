package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.entity.Post;
import com.workfront.internship.workflow.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nane on 7/17/17
 */
@RestController
public class AnswerRestController {

    private PostService postService;

    @Autowired
    public AnswerRestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = {"/edit-answer"}, method = RequestMethod.POST)
    public ResponseEntity<?> editAnswer(@RequestParam("answerId") String answerId,
                                   @RequestParam("content") String content) {
        try {
            Post answer = postService.getById(Long.valueOf(answerId));
            answer.setContent(content);

            postService.update(answer);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
