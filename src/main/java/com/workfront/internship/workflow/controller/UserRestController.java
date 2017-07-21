package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vahag on 7/4/2017
 */
@RestController
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/subscribe/*", method = RequestMethod.POST)
    public ResponseEntity<?> subscribe(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        long appAreaId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        User user = (User) request.getSession().getAttribute(PageAttributes.USER);
        String subscribed = request.getParameter(PageAttributes.NOTE);

        try {
                userService.subscribeToArea(user.getId(), appAreaId);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("OK");
    }

    @RequestMapping(value = "/unsubscribe/*", method = RequestMethod.POST)
    public ResponseEntity<?> unsubscribe(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        long appAreaId = Long.parseLong(url.substring(url.lastIndexOf('/') + 1));

        User user = (User) request.getSession().getAttribute(PageAttributes.USER);

        try {
            userService.unsubscribeToArea(user.getId(), appAreaId);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("OK");
    }

}
