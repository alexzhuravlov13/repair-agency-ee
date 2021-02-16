package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class CreateRepairFormPageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return "/user_add_repair_form.jsp";
    }
}
