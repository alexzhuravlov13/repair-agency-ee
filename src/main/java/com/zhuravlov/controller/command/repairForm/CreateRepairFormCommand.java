package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;
import com.zhuravlov.service.UserService;

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

        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        RepairFormService.getInstance().create(car, shortDescription, description, user);

        return "redirect:/user/userRepairFormList";
    }
}
