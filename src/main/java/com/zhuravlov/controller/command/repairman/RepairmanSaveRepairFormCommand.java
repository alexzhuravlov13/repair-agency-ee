package com.zhuravlov.controller.command.repairman;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RepairmanSaveRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        HttpSession session = req.getSession();

        RepairFormEntity editedForm = (RepairFormEntity) session.getAttribute("editedForm");

        if (req.getParameter("status") != null) {
            Status status = Status.valueOf(req.getParameter("status"));
            RepairFormService service = new RepairFormService();
            updateRepairForm(editedForm, status, service);
        }

        return "redirect:/repairman/repairmanRepairFormList";
    }

    private void updateRepairForm(RepairFormEntity editedForm, Status status, RepairFormService service) {
        editedForm.setStatus(status);
        service.updateRepairForm(editedForm);
    }

}
