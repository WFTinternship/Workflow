package com.workfront.internship.workflow.dao.util;

import com.workfront.internship.workflow.domain.User;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vahag on 6/15/2017
 */
public class DAOUtil {

    private static final Logger LOGGER = Logger.getLogger(DAOUtil.class);

    public static final String id = "id";
    public static final String firstName = "first_name";
    public static final String lastName = "last_name";
    public static final String email = "email";
    public static final String password = "passcode";
    public static final String avatarURl = "avatar_url";
    public static final String rating = "rating";

    /**
     * Sets users fields values from result set
     */
    public static User userFromResultSet(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getLong(id));
            user.setFirstName(rs.getString(firstName));
            user.setLastName(rs.getString(lastName));
            user.setEmail(rs.getString(email));
            user.setPassword(rs.getString(password));
            user.setAvatarURL(rs.getString(avatarURl));
            user.setRating(rs.getInt(rating));

        } catch (SQLException e) {
            LOGGER.error("SQL exception");
            throw new RuntimeException(e);
        }
        return user;
    }
}
