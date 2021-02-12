package com.zhuravlov.controller.command;

import javax.servlet.http.HttpServletRequest;

public class UserRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/user_repair_form_list.jsp";
    }
}
