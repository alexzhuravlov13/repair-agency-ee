package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;

public class SaveReviewCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String feedback = request.getParameter("feedback");
        int id = (Integer) request.getSession().getAttribute("repairFormReviewId");
        RepairFormService service = new RepairFormService();
        service.saveReview(id, feedback);
        return "redirect:/user/userRepairFormList";
    }
}
