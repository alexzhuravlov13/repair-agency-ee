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
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        int userId = Integer.parseInt(request.getParameter("userId"));

        UserService service = new UserService(new UserDaoImpl());
        UserEntity user = service.findById(userId);

        Role[] roles = Role.values();

        request.setAttribute("editedUser", user);
        request.setAttribute("allRoles", roles);

        return "/admin_edit_user.jsp";
    }
}
