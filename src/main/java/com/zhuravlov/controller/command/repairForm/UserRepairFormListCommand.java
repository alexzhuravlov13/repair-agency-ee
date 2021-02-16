package com.zhuravlov.controller.command.repairForm;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String sortDir = request.getParameter("sortDir");
        if (sortDir == null) {
            sortDir = "asc";
        }

        String sortField = CommandUtility.getSortField(request);
        addListWithPagination(request, session, sortField, sortDir);

        session.setAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        session.setAttribute("basePath", "/app/user/userRepairFormList");

        return "/user_repair_form_list.jsp";
    }

    private void addListWithPagination(HttpServletRequest request, HttpSession session, String sortField, String sortDir) {
        int page = 1;
        int perPageSize = 100;
        int currentPage = 1;

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
        if (sortDir == null) {
            sortDir = "";
        }

        List<RepairFormEntity> all = service.findAll(perPageSize, offset, sortField, sortDir);
        int formsCount = service.getFormsCount();

        int totalPages = formsCount / perPageSize;
        if (formsCount % perPageSize != 0) {
            totalPages = formsCount / perPageSize + 1;
        }

        session.setAttribute("repairForms", all);
        session.setAttribute("perPageSize", perPageSize);
        session.setAttribute("currentPage", currentPage);
        session.setAttribute("totalPages", totalPages);
    }
}
