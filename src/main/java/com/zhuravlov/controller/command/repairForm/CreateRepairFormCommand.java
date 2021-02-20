package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class CreateRepairFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String car = request.getParameter("car");
        String shortDescription = request.getParameter("shortDescription");
        String description = request.getParameter("description");

        if(!CommandUtility.isValidated(car, shortDescription, description)){
            request.setAttribute("repairFormEmptyFields", "repairFormEmptyFields");
            return "/user_add_repair_form.jsp";
        }

        RepairFormService.getInstance()
                .create(RepairFormBuilder.getInstance()
                .setCar(car)
                .setDescription(description)
                .setShortDescription(shortDescription)
                .setCreationDate(LocalDateTime.now())
                .setStatus(Status.NEW)
                .setAuthor((UserEntity) request.getSession().getAttribute("user"))
                .build());

        return "redirect:/user/userRepairFormList";
    }
}
