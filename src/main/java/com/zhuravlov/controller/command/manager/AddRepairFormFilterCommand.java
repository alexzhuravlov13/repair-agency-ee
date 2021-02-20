package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddRepairFormFilterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        setFilters(request);

        return "redirect:/manager/managerRepairFormList";
    }

    private void setFilters(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Status status = null;
        String statusPar = request.getParameter("status");
        if (statusPar != null && !statusPar.isEmpty()) {
            status = Status.valueOf(statusPar);
        }

        Integer repairmanId = null;
        String repairmanPar = request.getParameter("repairman");
        if (repairmanPar != null && !repairmanPar.isEmpty()) {
            repairmanId = Integer.parseInt(repairmanPar);
        }
        session.setAttribute("statusFilter", status);
        session.setAttribute("repairmanFilter", repairmanId);
    }
}
