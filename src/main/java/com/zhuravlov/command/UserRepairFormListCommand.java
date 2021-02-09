package com.zhuravlov.command;

import com.zhuravlov.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Role role = (Role) session.getAttribute("role");

        System.out.println("UserListCommand:" + role);

        if (!role.equals(Role.ADMIN)) {
            return "/error.jsp";
        }

        return "redirect: /user/userRepairFormList";
    }
}
