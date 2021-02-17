package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ViewRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

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
        session.setAttribute("repairForm", repairForm);
        return "/user_repair_form_view.jsp";
    }
}
