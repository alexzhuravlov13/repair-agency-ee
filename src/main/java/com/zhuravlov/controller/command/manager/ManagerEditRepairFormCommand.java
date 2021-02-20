package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class ManagerEditRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserService service = new UserService();

        int repairFormId = Integer.parseInt(request.getParameter("repairFormId"));

        RepairFormEntity repairForm = new RepairFormService().findById(repairFormId);

        List<Status> statuses = Arrays.asList(Status.CANCELED, Status.PAID, Status.WAITING_FOR_PAYMENT);
        List<UserEntity> repairmans = service.findRepairmans();
        request.setAttribute("statuses", statuses);
        session.setAttribute("repairmans", repairmans);
        session.setAttribute("editedForm", repairForm);
        session.setAttribute("editedFormId", repairFormId);
        return "/manager_repair_form_edit.jsp";
    }
}
