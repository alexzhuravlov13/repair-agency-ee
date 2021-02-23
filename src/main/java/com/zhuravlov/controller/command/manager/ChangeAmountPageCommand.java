package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeAmountPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        int userId = Integer.parseInt(request.getParameter("userId"));

        UserService service = new UserService(new UserDaoImpl());
        UserEntity user = service.findById(userId);

        request.setAttribute("editedUser", user);

        return "/manager_change_amount.jsp";
    }
}
