package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;

import javax.sql.DataSource;
import java.util.List;


public interface UserDAO {

    //CREATE

    /**
     * Adds user to the database
     * @param user user to be added
     * @return added user id
     */
    long add(User user);

    default long add(User user, DataSource dataSource){
        return 0;
    };



    //READ

    /**
     * Gets the list of the users by the given name
     * @param name name of the searched user
     * @return list of the user satisfying to the given name
     */
    List getByName(String name);

    /**
     * Gets the user by the given id
     * @param id id of the searched user
     * @return User with the given id
     */
    User getById(long id);

    /**
     * Gets the user by the given email
     * @param email email of the searched user
     * @return User with the given email
     */
    User getByEmail(String email);

    /**
     * Gets the list of AppAreas which the user with the given id is subscribed to
     * @param id user id
     * @return list of app areas of the given user
     */
    List<AppArea> getAppAreasById(long id);

    //UPDATE

    /**
     * Subscribes the given user to the given appArea
     * @param userId user id
     * @param appAreaId app area id
     */
    void subscribeToArea(long userId, long appAreaId);

    /**
     * Removes the subscription of the given user to the given appArea
     * @param userId user id
     * @param appAreaId app area id
     */
    void unsubscribeToArea(long userId, long appAreaId);

    //DELETE

    /**
     * Deletes the user with the given id
     * @param id id of the user that has to be deleted
     */
    void deleteById(long id);

    /**
     * Deletes all the existing users
     */
    void deleteAll();

}
