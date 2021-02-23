package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LoginCommand implements Command {
    UserService userService;

    public LoginCommand() {
        this.userService = new UserService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        if(!CommandUtility.isValidated(email, pass)){
            request.setAttribute("errorField", "emptyField");
            return "/login.jsp";
        }

        //getFromDb
        UserEntity user = userService.findByEmail(email);

        if (user == null || !user.getPassword().equals(CommandUtility.hashPass(pass, email))) {
            request.setAttribute("error", "no such user");
            return "/login.jsp";
        }

        if (CommandUtility.checkUserIsLoggedOrLogin(request, email)) {
            return "redirect:/error";
        }

        String userHomePage = CommandUtility.loginUserAndSendToHomePage(request, email, user);

        CommandUtility.initDataForEdit(request);

        if (userHomePage != null) return userHomePage;
        return "/login.jsp";
    }


}
