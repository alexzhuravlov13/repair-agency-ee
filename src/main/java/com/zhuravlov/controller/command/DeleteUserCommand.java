package com.zhuravlov.controller.command;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        int userId = Integer.parseInt(req.getParameter("userId"));

        UserService service = new UserService(new UserDaoImpl());
        service.delete(userId);

        //TODO:edit form
        return "redirect:/admin/listUsers";
    }
}
