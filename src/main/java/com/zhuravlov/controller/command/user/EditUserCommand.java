package com.zhuravlov.controller.command.user;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class EditUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();

        int userId = Integer.parseInt(req.getParameter("userId"));

        UserService service = new UserService(new UserDaoImpl());
        UserEntity user = service.findById(userId);

        Role[] roles = Role.values();

        session.setAttribute("editedUser", user);
        session.setAttribute("allRoles", roles);

        return "/admin_edit_user.jsp";
    }
}
