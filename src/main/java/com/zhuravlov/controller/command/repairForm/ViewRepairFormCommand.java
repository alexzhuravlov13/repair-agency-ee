package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ViewRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String basePath = (String) request.getSession().getAttribute("basePath");
        if (request.getParameter("repairFormId") == null) {
            return "redirect:/" + basePath;
        }
        int repairFormId = Integer.parseInt(request.getParameter("repairFormId"));

        RepairFormEntity repairForm = RepairFormService.getInstance().findById(repairFormId);

        request.setAttribute("repairForm", repairForm);
        return "/user_repair_form_view.jsp";
    }
}
