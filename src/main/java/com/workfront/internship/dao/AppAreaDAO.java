package com.workfront.internship.dao;

import com.workfront.internship.app.App;
import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;

import java.util.List;
import java.util.Map;


public interface AppAreaDAO {

    //CREATE

    long add(AppArea appArea);

    //READ

    List<User> getUsersById(long appAreaId);

    AppArea getById(long id);

    //UPDATE

    //DELETE

    void deleteById(long id);

}
