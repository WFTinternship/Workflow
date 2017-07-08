package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.entity.User;
import com.workfront.internship.workflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nane on 7/2/17
 */
@RestController
public class AuthenticationRestController {

    private static final String DEFAULT_AVATAR_URL = "/images/default/user-avatar.png";

    private final UserService userService;

    @Autowired
    public AuthenticationRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirmPass") String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setAvatarURL(DEFAULT_AVATAR_URL);
        try {
            if (userService.getByEmail(email) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                userService.add(user);
                userService.sendEmail(user);
                return ResponseEntity.ok(user.getEmail());
            }
        } catch (RuntimeException e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
