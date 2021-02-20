package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashSet;

public class RegisterCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!CommandUtility.isValidated(firstName, lastName, email, password)) {
            request.setAttribute(" status.error", "error");
            return "/registration.jsp";
        }

        UserService.getInstance().
                create(UserEntityBuilder.getInstance()
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setEmail(email)
                        .setPassword(password)
                        .setRoles(new HashSet<>(Collections.singletonList(Role.USER)))
                        .build());

        return "/login.jsp";
    }

}
