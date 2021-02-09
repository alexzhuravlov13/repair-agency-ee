package com.zhuravlov.command;

import com.zhuravlov.model.entity.Role;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)

        CommandUtility.setUserRole(request, Role.GUEST, "Guest");
        return "redirect:/index.jsp";
    }
}
