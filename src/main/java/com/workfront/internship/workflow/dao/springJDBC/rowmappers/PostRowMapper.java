package com.workfront.internship.workflow.dao.springJDBC.rowmappers;

import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.domain.Post;
import com.workfront.internship.workflow.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nane on 6/16/17
 */
public class PostRowMapper implements RowMapper {

    @Override
    public Post mapRow(ResultSet rs, int line) throws SQLException {
        return DAOUtil.postFromResultSet(rs);
    }
}