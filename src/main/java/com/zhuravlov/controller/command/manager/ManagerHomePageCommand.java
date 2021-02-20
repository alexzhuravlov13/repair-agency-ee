package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.Statement;
import java.util.List;

public class ManagerHomePageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        List<UserEntity> repairmans = new UserService().findRepairmans();
        request.getSession().setAttribute("repairmans", repairmans);
        request.getSession().setAttribute("allStatuses", Status.values());
        return "/manager_repair_form_list.jsp";
    }
}
