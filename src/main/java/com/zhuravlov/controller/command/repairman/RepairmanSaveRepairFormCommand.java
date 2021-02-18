package com.zhuravlov.controller.command.repairman;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class RepairmanSaveRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        HttpSession session = req.getSession();

        int id = (Integer) session.getAttribute("editedFormId");
        Status status = Status.valueOf(req.getParameter("status"));
        int repairmanId = Integer.parseInt(req.getParameter("repairman"));

        RepairFormEntity editedForm = (RepairFormEntity) session.getAttribute("editedForm");

        RepairFormService service = new RepairFormService();
        updateRepairForm(editedForm, session, status, repairmanId, service);


        return "redirect:/manager/managerRepairFormList";
    }

    private void updateRepairForm(RepairFormEntity editedForm, HttpSession session, Status status, int repairmanId, RepairFormService service) {
        editedForm.setStatus(status);
        List<UserEntity> repairmans = (List<UserEntity>) session.getAttribute("repairmans");
        UserEntity repairman = null;
        for (UserEntity userEntity : repairmans) {
            if (userEntity.getUserId() == repairmanId) {
                repairman = userEntity;
            }
        }
        editedForm.setRepairman(repairman);
        editedForm.setStatus(status);
        editedForm.setPrice(editedForm.getPrice());
        service.updateRepairForm(editedForm);
    }

}
