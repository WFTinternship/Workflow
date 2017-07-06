package com.workfront.internship.workflow.dao.springJDBC.rowmappers;

import com.workfront.internship.workflow.entity.AppArea;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vahag on 6/15/2017.
 */
public class AppAreaRowMapper implements RowMapper {



    @Override
    public AppArea mapRow(ResultSet rs, int rowNum) throws SQLException {
        int appAreaId = rs.getInt("apparea_id");
        return AppArea.getById(appAreaId);
    }
}
