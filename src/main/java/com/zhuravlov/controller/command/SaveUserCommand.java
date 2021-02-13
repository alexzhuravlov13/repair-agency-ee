package com.zhuravlov.controller.command;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class SaveUserCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        UserEntity user = getUserFromRequest(req);

        System.out.println(user);

        UserService service = new UserService(new UserDaoImpl());
        service.update(user);
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

        return new UserEntityBuilder()
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
