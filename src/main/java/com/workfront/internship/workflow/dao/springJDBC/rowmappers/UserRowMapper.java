package com.workfront.internship.workflow.dao.springJDBC.rowmappers;

import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vahag on 6/15/2017
 */
public class UserRowMapper implements RowMapper {

    @Override
    public User mapRow(ResultSet rs, int line) throws SQLException {
        return DAOUtil.userFromResultSet(rs);
    }

}
