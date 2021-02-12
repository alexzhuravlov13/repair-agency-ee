package com.zhuravlov.controller.command;

import com.zhuravlov.model.entity.Role;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

class CommandUtility {
    static void setUserRoles(HttpServletRequest request,
                             Set<Role> roles, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();

        context.setAttribute("userName", name);
        session.setAttribute("roles", roles);
    }

    static boolean checkUserIsLoggedOrLogin(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if (loggedUsers.stream().anyMatch(userName::equals)) {
            return true;
        }

        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

}
