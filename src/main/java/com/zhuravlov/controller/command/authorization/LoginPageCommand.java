package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LoginPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("#Command:" + this.getClass().getSimpleName());
        return "/login.jsp";
    }
}
