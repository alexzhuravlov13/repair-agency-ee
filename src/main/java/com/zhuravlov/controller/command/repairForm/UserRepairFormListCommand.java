package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.Role;

import javax.servlet.http.HttpServletRequest;

public class UserRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.addListWithPaginationAndSorting(request, Role.USER);
        request.getSession().setAttribute("basePath", "/app/user/userRepairFormList");

        return "/user_repair_form_list.jsp";
    }


}
