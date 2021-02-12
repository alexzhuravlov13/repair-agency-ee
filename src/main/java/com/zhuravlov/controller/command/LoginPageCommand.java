package com.zhuravlov.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LoginPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("#Command:" + this.getClass().getSimpleName());
        return "/login.jsp";
    }
}
