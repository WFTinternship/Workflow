package com.workfront.internship.workflow.dao.springJDBC.rowmappers;


import com.workfront.internship.workflow.dao.util.DAOUtil;
import com.workfront.internship.workflow.domain.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Angel on 6/15/2017
 */
public class CommentRowMapper implements RowMapper {

    @Override
    public Comment mapRow(ResultSet rs, int i) throws SQLException {
        return DAOUtil.commentFromResultSet(rs, "comment");
    }
}
