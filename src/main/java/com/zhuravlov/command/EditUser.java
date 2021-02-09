package com.zhuravlov.command;

import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

public class EditUser implements Command {
    @Override
    public String execute(HttpServletRequest req) {

        //int userId = Integer.parseInt(req.getParameter("userId"));

        //UserDaoImpl userDao = new UserDaoImpl();
        //UserEntity byId = userDao.findById(userId);

        //TODO:edit form
        return "/adminEditUser.jsp";
    }
}
