package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddRepairFormFilterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        CommandUtility.setFilters(request);

        return "redirect:/manager/managerRepairFormList";
    }

}
