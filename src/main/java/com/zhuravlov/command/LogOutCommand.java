package com.zhuravlov.command;

import com.zhuravlov.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        // ToDo delete current user (context & session)

        CommandUtility.setUserRoles(request, new HashSet<>(Collections.singletonList(Role.GUEST)),
                "Guest");
        return "redirect:/login";
    }
}
