package com.zhuravlov.command;

import com.zhuravlov.db.MockDb;
import com.zhuravlov.model.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Register implements Command {
    private static final Logger log = LogManager.getLogger(Register.class);

    @Override
    public String execute(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        MockDb.users.put(email, new UserEntity(firstName, lastName, email, password));

        HttpSession session = req.getSession();
        session.setAttribute("usersMap", MockDb.users);

        return "/users.jsp";
    }
}
