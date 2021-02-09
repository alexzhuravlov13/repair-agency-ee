package com.zhuravlov.command;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserList implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();

        Role role = (Role) session.getAttribute("role");

        System.out.println("UserListCommand:" + role);

        if (role==null || !role.equals(Role.ADMIN)) {
            return "/error.jsp";
        }

        List<UserEntity> all = new UserDaoImpl().findAll();
        session.setAttribute("users", all);


        return "/usersAdminList.jsp";
    }
}
