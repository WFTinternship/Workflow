package com.workfront.internship.workflow.service;

import com.workfront.internship.workflow.domain.AppArea;
import com.workfront.internship.workflow.domain.User;

import java.util.List;


public interface AppAreaService {

    //CREATE

    /**
     * Adds AppArea
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
