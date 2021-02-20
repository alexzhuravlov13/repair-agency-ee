package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ClearRepairFormFilterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("statusFilter", null);
        session.setAttribute("repairmanFilter", null);

        return "redirect:/manager/managerRepairFormList";
    }
}
