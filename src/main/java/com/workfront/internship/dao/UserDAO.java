package com.workfront.internship.dao;

import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;

import java.util.List;

/**
 * Created by naneh on 5/27/17.
 */
public interface UserDAO {

    boolean add(User user);

    boolean subscribeToArea(User user, AppArea appArea);

    List<User> getByName(String name);

}
