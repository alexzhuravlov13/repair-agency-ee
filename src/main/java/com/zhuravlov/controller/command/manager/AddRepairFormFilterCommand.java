package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
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
        Status status = null;
        String statusPar = request.getParameter("status");
        if(CommandUtility.isValidated(statusPar)){
            status = Status.valueOf(statusPar);
        }

        Integer repairmanId = null;
        String repairmanPar = request.getParameter("repairman");
        if (CommandUtility.isValidated(repairmanPar)) {
            repairmanId = Integer.parseInt(repairmanPar);
        }

        HttpSession session = request.getSession();
        session.setAttribute("statusFilter", status);
        session.setAttribute("repairmanFilter", repairmanId);
    }
}
