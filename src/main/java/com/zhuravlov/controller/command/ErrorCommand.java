package com.zhuravlov.controller.command;

import javax.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        Integer code = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String message =  (String) request.getAttribute("javax.servlet.error.message");
        String exception_type =  (String) request.getAttribute("javax.servlet.error.exception_type");
        String exception =  (String) request.getAttribute("javax.servlet.error.exception");

        request.getSession().setAttribute("errorType", exception_type);
        request.getSession().setAttribute("errorMessage", message);
        request.getSession().setAttribute("exception", exception);
        request.getSession().setAttribute("errorCode", code);
        return "/error.jsp";

    }
}
