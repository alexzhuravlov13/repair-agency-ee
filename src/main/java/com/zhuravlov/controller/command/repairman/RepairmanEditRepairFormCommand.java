package com.zhuravlov.controller.command.repairman;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.service.RepairFormService;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

public class RepairmanEditRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserService service = new UserService();

        int repairFormId = Integer.parseInt(request.getParameter("repairFormId"));

        RepairFormEntity repairForm = new RepairFormService().findById(repairFormId);

        List<Status> statuses = Arrays.asList(Status.READY, Status.IN_PROGRESS);
        session.setAttribute("statuses", statuses);
        session.setAttribute("editedForm", repairForm);
        session.setAttribute("editedFormId", repairFormId);
        return "/repairman_repair_form_edit.jsp";
    }
}
