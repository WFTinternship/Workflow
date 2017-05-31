package com.workfront.internship.dao;

import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;

import java.util.List;


public interface UserDAO {

    long add(User user);

    void deleteById(long id);

    void deleteAll();

    void subscribeToArea(long userId, long appAreaId);

    List<User> getByName(String name);

    User getById(long id);

    List<AppArea> getAppAreasById(long id);

}
