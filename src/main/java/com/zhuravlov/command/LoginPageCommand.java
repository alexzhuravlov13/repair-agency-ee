package com.zhuravlov.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("#Command:" + this.getClass().getSimpleName());
        return "/login.jsp";
    }
}
