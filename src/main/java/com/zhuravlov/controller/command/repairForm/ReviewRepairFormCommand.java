package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReviewRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        int repairFormId = Integer.parseInt(request.getParameter("repairFormId"));

        RepairFormEntity repairForm = new RepairFormService().findById(repairFormId);

        request.setAttribute("repairForm", repairForm);
        session.setAttribute("repairFormReviewId", repairFormId);
        return "/user_repair_form_review.jsp";
    }
}
