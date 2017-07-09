package com.workfront.internship.workflow.entity.converter;

import com.workfront.internship.workflow.entity.AppArea;
import com.workfront.internship.workflow.entity.AppAreaEnum;

import java.awt.Color;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Vahag on 7/8/2017
 */

@Converter
public class AppAreaConverter implements AttributeConverter<AppAreaEnum, String> {

    @Override
    public String convertToDatabaseColumn(AppAreaEnum appArea) {
        return String.valueOf(appArea.getId());
    }


    @Override
    public AppAreaEnum convertToEntityAttribute(String id) {
        return AppAreaEnum.getById(Long.valueOf(id));
    }

}