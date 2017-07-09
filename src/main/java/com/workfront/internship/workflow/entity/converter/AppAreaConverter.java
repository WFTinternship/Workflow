package com.workfront.internship.workflow.entity.converter;

import com.workfront.internship.workflow.entity.AppArea;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Vahag on 7/8/2017
 */

@Converter
public class AppAreaConverter implements AttributeConverter<AppArea, String> {

    @Override
    public String convertToDatabaseColumn(AppArea appArea) {
        return String.valueOf(appArea.getId());
    }


    @Override
    public AppArea convertToEntityAttribute(String id) {
        return AppArea.getById(Long.valueOf(id));
    }

}