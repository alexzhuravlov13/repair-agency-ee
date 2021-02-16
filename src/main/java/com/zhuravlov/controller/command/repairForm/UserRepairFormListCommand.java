package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        int page = 1;
        int perPageSize = 5;
        int currentPage = 1;

        String basePath = "/app/user/userRepairFormList";

        String pagePar = request.getParameter("page");
        if (pagePar != null) {
            page = Integer.parseInt(pagePar);
            currentPage = page;
        }

        int offset = 0;

        if (page > 1) {
            offset = (page - 1) * perPageSize;
        }

        RepairFormService service = new RepairFormService();
        List<RepairFormEntity> all = service.findAll(perPageSize, offset);
        int formsCount = service.getFormsCount();

        int totalPages = formsCount / perPageSize;
        if (formsCount % perPageSize != 0) {
            totalPages = formsCount / perPageSize + 1;
        }

        /*modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");*/


        session.setAttribute("repairForms", all);
        session.setAttribute("perPageSize", perPageSize);
        session.setAttribute("currentPage", currentPage);
        session.setAttribute("basePath", basePath);
        session.setAttribute("totalPages", totalPages);

        return "/user_repair_form_list.jsp";
    }
}
