package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LoginCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(LoginCommand.class);


    UserService userService;

    public LoginCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        HttpSession session = request.getSession();
        request.setAttribute("errorField", null);
        request.setAttribute("error", null);

        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            request.setAttribute("errorField", "emptyField");
            return "/login.jsp";
        }

        //getFromDb
        UserEntity user = userService.findByEmail(email);

        if (user == null) {
            request.setAttribute("error", "no such user");
            return "/login.jsp";
        }

        //Ifloggined-quit
        if (CommandUtility.checkUserIsLoggedOrLogin(request, email)) {
            return "redirect:/error";
        }

        Set<Role> userRoles = user.getRoles();
        session.setAttribute("user", user);
        CommandUtility.setUserRoles(request, userRoles, email);

        initDataForEdit(request);

        String userHomePage = getUserHomePage(userRoles);
        if (userHomePage != null) return userHomePage;
        return "/login.jsp";
    }

    private String getUserHomePage(Set<Role> userRoles) {
        if (userRoles.contains(Role.ADMIN)) {
            return "redirect:/admin/listUsers";
        } else if (userRoles.contains(Role.MANAGER)) {
            return "redirect:/manager/home";
        } else if (userRoles.contains(Role.REPAIRMAN)) {
            return "redirect:/repairman/repairmanRepairFormList";
        } else if (userRoles.contains(Role.USER)) {
            return "redirect:/user/userRepairFormList";
        }
        return null;
    }

    private void initDataForEdit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("roleAdmin", Role.ADMIN);
        session.setAttribute("roleManager", Role.MANAGER);
        session.setAttribute("roleRepairman", Role.REPAIRMAN);
        session.setAttribute("statusReady", Status.READY);
        session.setAttribute("statusCanceled", Status.CANCELED);
    }

}
