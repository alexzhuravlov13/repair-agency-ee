package com.zhuravlov.controller.command.authorization;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RegisterCommand implements Command {
    private static final Logger log = LogManager.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            HttpSession session = request.getSession();
            request.setAttribute(" status.error", "errorrrrrrrrrrrrrr");
            return "/registration.jsp";
        }

        UserEntity userEntity = new UserEntity(firstName, lastName, email, password);

        userEntity.getRoles().add(Role.USER);

        saveToDb(userEntity);

        return "/login.jsp";
    }

    private void saveToDb(UserEntity user) {
        UserService userService = new UserService(new UserDaoImpl());
        userService.create(user);
    }


}
