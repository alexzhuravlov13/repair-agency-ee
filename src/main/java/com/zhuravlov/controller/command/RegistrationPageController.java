package com.zhuravlov.controller.command;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/registration.jsp";
    }
}
