package com.workfront.internship.workflow.dao;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;

import java.util.List;


public interface AppAreaDAO {

    String id = "id";
    String name = "name";
    String description = "description";
    String teamName = "team_name";

    //CREATE

    /**
     * Adds AppArea to the database
     * @param appArea app area to be added
     * @return id of the added app area
     */
    long add(AppArea appArea);

    //READ

    /**
     * Gets the list of users who are subscribed to the appArea with the given id
     * @param appAreaId app area id
     * @return users of the given app area
     */
    List<User> getUsersById(long appAreaId);

    /**
     * Gets the AppArea with the given id
     * @param id id of the searched app area
     * @return app area with the given id
     */
    AppArea getById(long id);

    //UPDATE

    //DELETE

    /**
     * Deletes AppArea with the given id
     * @param id app area id that has to be deleted
     */
    void deleteById(long id);

   }
