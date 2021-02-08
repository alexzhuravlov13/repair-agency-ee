package com.zhuravlov.command;

import com.zhuravlov.db.MockDb;
import com.zhuravlov.model.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/registration.jsp";
    }
}
