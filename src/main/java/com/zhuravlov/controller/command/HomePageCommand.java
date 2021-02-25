package com.zhuravlov.controller.command;

import com.zhuravlov.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class HomePageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return CommandUtility.getUserHomePage((Set<Role>) request.getSession().getAttribute("roles"));
    }
}

