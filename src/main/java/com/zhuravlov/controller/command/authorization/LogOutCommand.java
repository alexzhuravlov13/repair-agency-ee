package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.Role;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashSet;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String userName = (String) session.getAttribute("userName");

        if (CommandUtility.checkUserIsLoggedOrLogin(request, userName)) {
            CommandUtility.logOut(request, session, userName);
        }

        return "redirect:/login";
    }


}
