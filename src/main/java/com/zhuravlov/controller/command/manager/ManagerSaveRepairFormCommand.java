package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class ManagerSaveRepairFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        if (!CommandUtility.isValidated(request.getParameter("status"),
                request.getParameter("repairman"),
                request.getParameter("price"))) {

            request.setAttribute("repairFormEmptyFields", "repairFormEmptyFields");
            return "/manager_repair_form_edit.jsp";
        }

        return saveRepairForm(request);
    }

    private String saveRepairForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int id = (Integer) session.getAttribute("editedFormId");
        Status status = Status.valueOf(request.getParameter("status"));
        int repairmanId = Integer.parseInt(request.getParameter("repairman"));
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(request.getParameter("price")));

        RepairFormEntity editedForm = (RepairFormEntity) session.getAttribute("editedForm");

        RepairFormService service = new RepairFormService();
        if (status.equals(Status.PAID)) {
            if (!service.writeOffFunds(id, editedForm.getAuthor().getUserId(), status, repairmanId, price)) {
                request.setAttribute("errorMoney", "errorMoney");
                return "/manager_repair_form_edit.jsp";
            }
        } else {
            updateRepairForm(editedForm, session, status, repairmanId, price, service);
        }

        return "redirect:/manager/managerRepairFormList";
    }

    public RepairFormEntity updateRepairForm(RepairFormEntity editedForm, HttpSession session, Status status, int repairmanId, BigDecimal price, RepairFormService service) {
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
        editedForm.setPrice(price);
        service.updateRepairForm(editedForm);

        return editedForm;
    }

}
