package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.Servlet;
import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;

public class RegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!CommandUtility.isValidated(firstName, lastName, email, password)) {
            request.setAttribute("fieldsError", "fieldsError");
            return "/registration.jsp";
        }

        if (!CommandUtility.isEmailCorrect(email)) {
            request.setAttribute("emailError", "emailError");
            return "/registration.jsp";
        }

        if (UserService.getInstance().findByEmail(email) != null) {
            request.setAttribute("errorRegistered", "errorRegistered");
            return "/registration.jsp";
        }

        UserService.getInstance().registerUser(firstName, lastName, email, password);

        return "/login.jsp";
    }


}
