package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface UserService {

    //CREATE

    /**
     * Adds user if is valid
     * @param user
     * @return
     */
    long add(User user);

    //READ

    /**
     * Gets the list of the users by the given name
     * @param name
     * @return
     */
    List<User> getByName(String name);

    /**
     * Gets the user by the given id
     * @param id
     * @return
     */
    User getById(long id);

    /**
     * Gets user by the given email
     * @param email
     * @return
     */
    User getByEmail(String email);

    /**
     * Gets the list of AppAreas which the user with the given id is subscribed to
     * @param id
     * @return
     */
    List<AppArea> getAppAreasById(long id);

    //UPDATE

    /**
     * Subscribes the given user to the given appArea
     * @param userId
     * @param appAreaId
     */
    void subscribeToArea(long userId, long appAreaId);

    /**
     * Removes the subscription of the given user to the given appArea
     * @param userId
     * @param appAreaId
     */
    void unsubscribeToArea(long userId, long appAreaId);

    //DELETE

    /**
     * Deletes the user with the given id
     * @param id
     */
    void deleteById(long id);

    /**
     * Deletes all the existing users
     */
    void deleteAll();

    /**
     * Checks whether the email and password combination exists in database
     * @param email is input from client
     * @param password is input from client
     * @return user with specified email, is the password is correct
     */
    User authenticate(String email, String password);


    String saveAvatar(String uploadPath, MultipartFile image)  throws IOException;

    boolean isValidImage(MultipartFile image);

    /**
     * Sends email to a new user
     * @param user is the one to sign up
     */
    void sendEmail(User user);
}
