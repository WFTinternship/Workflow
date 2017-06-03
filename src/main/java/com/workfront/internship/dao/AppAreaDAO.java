package com.workfront.internship.dao;

import com.workfront.internship.app.App;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;

import java.util.List;
import java.util.Map;


public interface AppAreaDAO {

    //CREATE

    /**
     * Adds AppArea to the database
     * @param appArea
     * @return
     */
    long add(AppArea appArea);

    //READ

    /**
     * Gets the list of users who are subscribed to the appArea with the given id
     * @param appAreaId
     * @return
     */
    List<User> getUsersById(long appAreaId);

    /**
     * Gets the AppArea with the given id
     * @param id
     * @return
     */
    AppArea getById(long id);

    //UPDATE

    //DELETE

    /**
     * Deletes AppArea with the given id
     * @param id
     */
    void deleteById(long id);

}
