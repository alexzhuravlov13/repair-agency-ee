package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class ManagerEditRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        //?repairFormId=19
        HttpSession session = request.getSession();
        UserService service = new UserService();
        List<UserEntity> repairmans = service.findRepairmans();

        int repairFormId = Integer.parseInt(request.getParameter("repairFormId"));
        List<RepairFormEntity> repairFormEntities =
                (List<RepairFormEntity>) session
                        .getAttribute("repairForms");

        RepairFormEntity repairForm = null;
        for (RepairFormEntity repairFormEntity : repairFormEntities) {
            if (repairFormEntity.getId() == repairFormId) {
                repairForm = repairFormEntity;
            }
        }

        List<Status> statuses = Arrays.asList(Status.CANCELED, Status.PAID, Status.WAITING_FOR_PAYMENT);
        session.setAttribute("repairmans", repairmans);
        session.setAttribute("statuses", statuses);
        session.setAttribute("editedForm", repairForm);
        session.setAttribute("editedFormId", repairForm.getId());
        return "/manager_repair_form_edit.jsp";
    }
}
