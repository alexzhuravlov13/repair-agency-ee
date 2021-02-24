package com.zhuravlov.controller.command.user;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class EditUserCommand implements Command {
    private final UserService service;

    @Override
    public String execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));

        request.getSession().setAttribute("editedUser", service.findById(userId));
        request.getSession().setAttribute("allRoles", Role.values());

        return "/admin_edit_user.jsp";
    }

    public EditUserCommand(UserService service) {
        this.service = service;
    }

    public EditUserCommand() {
        this.service = UserService.getInstance();
    }
}
