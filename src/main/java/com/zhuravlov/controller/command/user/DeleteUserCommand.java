package com.zhuravlov.controller.command.user;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        int userId = Integer.parseInt(req.getParameter("userId"));

        UserService service = new UserService(new UserDaoImpl());
        service.delete(userId);

        return "redirect:/admin/listUsers";
    }
}
