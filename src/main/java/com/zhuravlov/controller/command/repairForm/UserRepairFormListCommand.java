package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class UserRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        int page = 1;
        int perPageSize = 5;
        String pagePar = request.getParameter("page");
        if (pagePar != null) {
            page = Integer.parseInt(pagePar);
        }

        int maxPage = 10;
        int start = page;

        if (page > 1) {
            start = page * perPageSize;
        }

        UserService service = new UserService(new UserDaoImpl());
        service.findAll();

        /*modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");*/

        return "/user_repair_form_list.jsp";
    }
}
