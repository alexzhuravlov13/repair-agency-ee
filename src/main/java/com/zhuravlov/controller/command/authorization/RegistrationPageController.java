package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class RegistrationPageController implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/registration.jsp";
    }
}
