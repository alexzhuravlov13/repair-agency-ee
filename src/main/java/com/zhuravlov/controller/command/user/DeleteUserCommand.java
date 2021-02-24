package com.zhuravlov.controller.command.user;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements Command {
    private final UserService service;

    @Override
    public String execute(HttpServletRequest req) {

        int userId = Integer.parseInt(req.getParameter("userId"));

        service.delete(userId);

        return "redirect:/admin/listUsers";
    }

    public DeleteUserCommand(UserService service) {
        this.service = service;
    }

    public DeleteUserCommand() {
        this.service = UserService.getInstance();
    }
}
