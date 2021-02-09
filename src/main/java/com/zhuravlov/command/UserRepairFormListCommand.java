package com.zhuravlov.command;

import com.zhuravlov.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return "redirect: /user/userRepairFormList";
    }
}
