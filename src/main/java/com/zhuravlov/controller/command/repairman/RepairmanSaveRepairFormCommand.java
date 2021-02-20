package com.zhuravlov.controller.command.repairman;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RepairmanSaveRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (!CommandUtility.isValidated(request.getParameter("status"))) {
            request.setAttribute("repairFormEmptyFields", "repairFormEmptyFields");
            return "/repairman_repair_form_edit.jsp";
        }

        HttpSession session = request.getSession();

        RepairFormEntity editedForm = (RepairFormEntity) session.getAttribute("editedForm");
        Status status = Status.valueOf(request.getParameter("status"));
        editedForm.setStatus(status);
        RepairFormService.getInstance().updateRepairForm(editedForm);

        return "redirect:/repairman/repairmanRepairFormList";
    }
}
