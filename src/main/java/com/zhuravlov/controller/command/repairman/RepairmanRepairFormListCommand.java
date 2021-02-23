package com.zhuravlov.controller.command.repairman;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.Role;

import javax.servlet.http.HttpServletRequest;

public class RepairmanRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.addListWithPaginationAndSorting(request, Role.REPAIRMAN);
        request.getSession().setAttribute("basePath", "/app/repairman/repairmanRepairFormList");

        return "/repairman_repair_form_list.jsp";
    }

}
