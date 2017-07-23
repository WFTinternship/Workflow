package com.workfront.internship.workflow.controller;

import com.workfront.internship.workflow.exceptions.service.InvalidObjectException;
import com.workfront.internship.workflow.exceptions.service.ServiceLayerException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Vahag on 7/20/2017
 */
public abstract class BaseController {

    @ExceptionHandler({InvalidObjectException.class, ServiceLayerException.class})
    protected ModelAndView handleError() {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject(PageAttributes.MESSAGE, "INTERNAL SERVER ERROR");
        return modelAndView;
    }

}
