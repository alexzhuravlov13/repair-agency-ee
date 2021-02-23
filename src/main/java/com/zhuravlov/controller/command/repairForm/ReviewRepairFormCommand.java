package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;

public class ReviewRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        int repairFormId = Integer.parseInt(request.getParameter("repairFormId"));
        RepairFormEntity byId = RepairFormService.getInstance().findById(repairFormId);
        if (byId == null) {
            return "redirect:/user/userRepairFormList";
        }
        request.setAttribute("repairForm", byId);
        request.getSession().setAttribute("repairFormReviewId", repairFormId);
        return "/user_repair_form_review.jsp";
    }
}
