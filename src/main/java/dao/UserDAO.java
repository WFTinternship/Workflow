package dao;

import dataModel.AppArea;
import dataModel.User;

import java.util.List;

/**
 * Created by nane on 5/27/17.
 */
public interface UserDAO {

    boolean add(User user);

    boolean subscribeToArea(User user, AppArea appArea);

    List<User> getByName(String name);

}
