package com.zhuravlov.command;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.db.DbUtil;
import com.zhuravlov.db.MockDb;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;

public class Register implements Command {
    private static final Logger log = LogManager.getLogger(Register.class);

    @Override
    public String execute(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
            HttpSession session = req.getSession();
            //TODO:error message
            session.setAttribute(" status.error", "errorrrrrrrrrrrrrr");
            return "/registration.jsp";
        }

        UserEntity userEntity = new UserEntity(firstName, lastName, email, password);
        userEntity.getRoles().add(Role.ADMIN);
        userEntity.getRoles().add(Role.USER);

        saveToDb(userEntity);

        return "/login.jsp";
    }

    private void saveToDb(UserEntity user){
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.create(user);
    }


}
