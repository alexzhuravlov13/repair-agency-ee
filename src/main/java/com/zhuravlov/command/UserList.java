package com.zhuravlov.command;

import com.zhuravlov.db.Dao.UserDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserList implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("users", new UserDaoImpl().findAll());

        return "/usersAdminList.jsp";
    }
}
