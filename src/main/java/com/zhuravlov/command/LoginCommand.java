package com.zhuravlov.command;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command{
    private static Logger log = LoggerFactory.getLogger(LoginCommand.class);


    UserDaoImpl userDao;

    public LoginCommand() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("execute");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        if( email == null || email.equals("") || pass == null || pass.equals("")  ){
            System.out.println("not");
            //TODO:error message
            return "/login.jsp";
        }


        System.out.println(email + " " + pass);
        //getFromDb
        UserEntity user = userDao.findByEmail(email);
        System.out.println("User:"+ user);
        if (user==null){
            return "redirect:/login.jsp";
        }

        //Ifloggined-quit
        System.out.println("checkUserIsLoggedOrLogin");
        if(CommandUtility.checkUserIsLoggedOrLogin(request, email)){
            return "/error.jsp";
        }

        if (user.getRoles().contains(Role.ADMIN)){
            System.out.println("CommandUtility.setUserRoleAdmin");
            CommandUtility.setUserRole(request, Role.ADMIN, email);
            return "redirect:/admin/userList";
        } else if(user.getRoles().contains(Role.USER)) {
            System.out.println("CommandUtility.setUserRoleUser");
            CommandUtility.setUserRole(request, Role.USER, email);
            return "redirect:/user/userRepairFormList";
        } else {
            System.out.println("CommandUtility.setUserRoleGUEST");
            CommandUtility.setUserRole(request, Role.GUEST, email);
            return "/login.jsp";
        }
    }

}
