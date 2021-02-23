package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.dto.RepairFormDto;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ManagerRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.addListWithPaginationAndSorting(request, Role.MANAGER);
        request.getSession().setAttribute("basePath", "/app/manager/managerRepairFormList");

        return "/manager_repair_form_list.jsp";
    }
}
