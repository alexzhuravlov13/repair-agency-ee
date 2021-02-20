package com.zhuravlov.controller.command.user;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SaveUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        if (!CommandUtility.isValidated(req.getParameter("userId"),
                req.getParameter("amount"),
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("email"),
                req.getParameter("password")) || req.getParameterValues("roles") == null) {
            req.setAttribute("userEmptyFields", "userEmptyFields");
            return "/admin_edit_user.jsp";
        }

        UserService.getInstance().update(getUserFromRequest(req));
        return "redirect:/admin/listUsers";
    }

    private UserEntity getUserFromRequest(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("userId"));
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("amount")));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String[] rolesArr = req.getParameterValues("roles");
        Set<Role> roles = Arrays.stream(rolesArr).map(Role::valueOf).collect(Collectors.toSet());

        return UserEntityBuilder.getInstance()
                .setUserId(id)
                .setAmount(amount)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setRoles(roles)
                .build();
    }
}
