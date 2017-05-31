package com.workfront.internship.dao;

import com.workfront.internship.dataModel.AppArea;
import com.workfront.internship.dataModel.User;

import java.util.List;
import java.util.Map;

/**
 * Created by nane on 5/27/17.
 */
public interface AppAreaDAO {

    long add(AppArea appArea);

    void deleteById(long id);

    List<User> getUsersById(long appAreaId);
}
