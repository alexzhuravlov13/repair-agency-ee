package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;

public class SaveReviewCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String feedback = request.getParameter("feedback");

        if(!CommandUtility.isValidated(feedback)){
            request.setAttribute("repairFormEmptyFields", "repairFormEmptyFields");
            return "/user_repair_form_review.jsp";
        }

        int id = (Integer) request.getSession().getAttribute("repairFormReviewId");

        RepairFormService.getInstance().saveReview(id, feedback);
        return "redirect:/user/userRepairFormList";
    }
}
