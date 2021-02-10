package com.zhuravlov.command;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LoginCommand implements Command {
    private static Logger log = LoggerFactory.getLogger(LoginCommand.class);


    UserDaoImpl userDao;

    public LoginCommand() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        HttpSession session = request.getSession();
        session.setAttribute("errorField", null);
        session.setAttribute("error", null);

        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            session.setAttribute("errorField", "emptyField");
            return "/login.jsp";
        }


        System.out.println(email + " " + pass);
        //getFromDb
        UserEntity user = userDao.findByEmail(email);
        System.out.println("User:" + user);
        if (user == null) {
            session.setAttribute("error", "no such user");
            return "/login.jsp";
        }

        //Ifloggined-quit
        System.out.println("checkUserIsLoggedOrLogin");
        if (CommandUtility.checkUserIsLoggedOrLogin(request, email)) {
            return "/error.jsp";
        }

        Set<Role> userRoles = user.getRoles();

        System.out.println("#CommandUtility.setUserRole");
        CommandUtility.setUserRoles(request, userRoles, email);
        return "redirect:/home";
    }

}
