package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;

import java.sql.Connection;
import java.util.List;


public interface UserDAO {

    //CREATE

    /**
     * Adds user to the database
     * @param user
     * @return
     */
    long add(User user);

    long add(User user, Connection connection);



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
     * Gets the user by the given email
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

}
