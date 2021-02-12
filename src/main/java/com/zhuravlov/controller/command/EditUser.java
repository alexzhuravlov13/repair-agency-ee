package com.zhuravlov.controller.command;

import javax.servlet.http.HttpServletRequest;

public class EditUser implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        //int userId = Integer.parseInt(req.getParameter("userId"));

        //UserDaoImpl userDao = new UserDaoImpl();
        //UserEntity byId = userDao.findById(userId);

        //TODO:edit form
        return "/adminEditUser.jsp";
    }
}
