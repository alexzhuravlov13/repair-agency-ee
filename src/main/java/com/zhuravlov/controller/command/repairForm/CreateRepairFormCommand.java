package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
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

        RepairFormEntity repairFormEntity = new RepairFormBuilder()
                .setCar(car)
                .setDescription(description)
                .setShortDescription(shortDescription)
                .setCreationDate(LocalDateTime.now())
                .setStatus(Status.NEW)
                .setAuthor((UserEntity) request.getSession().getAttribute("user"))
                .build();
        System.out.println(repairFormEntity);
        RepairFormService service = new RepairFormService();
        service.create(repairFormEntity);
        return "redirect:/user/userRepairFormList";
    }
}
