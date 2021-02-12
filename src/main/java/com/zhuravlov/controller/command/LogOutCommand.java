package com.zhuravlov.controller.command;

import com.zhuravlov.model.entity.Role;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashSet;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        //TODO: delete current user (context & session)
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();

        String userName = (String) context.getAttribute("userName");

        if (CommandUtility.checkUserIsLoggedOrLogin(request, userName)) {
            logOut(request, session, userName);
        }

        return "redirect:/login";
    }

    private void logOut(HttpServletRequest request, HttpSession session, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.remove(userName);

        CommandUtility.setUserRoles(request, new HashSet<>(Collections.singletonList(Role.GUEST)),
                "Guest");

        request.getServletContext().setAttribute("userName", "");

        request.getSession().invalidate();
    }
}
