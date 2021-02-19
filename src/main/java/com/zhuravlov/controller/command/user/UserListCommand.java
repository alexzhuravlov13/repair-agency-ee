package com.zhuravlov.controller.command.user;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<UserEntity> all = new UserService(new UserDaoImpl()).findAll();
        request.setAttribute("users", all);

        return "/admin_users_list.jsp";
    }
}
