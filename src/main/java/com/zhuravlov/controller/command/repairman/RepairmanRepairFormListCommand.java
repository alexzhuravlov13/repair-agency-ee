package com.zhuravlov.controller.command.repairman;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
import com.zhuravlov.model.dto.RepairFormDto;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class RepairmanRepairFormListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String sortDir = request.getParameter("sortDir");
        if (sortDir == null) {
            sortDir = "asc";
        }

        String sortField = CommandUtility.getSortField(request);
        addListWithPagination(request, session, sortField, sortDir);

        request.setAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        session.setAttribute("basePath", "/app/repairman/repairmanRepairFormList");

        return "/repairman_repair_form_list.jsp";
    }

    private void addListWithPagination(HttpServletRequest request, HttpSession session, String sortField, String sortDir) {
        int page = 1;
        int perPageSize = 5;
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
        UserEntity user = (UserEntity) session.getAttribute("user");
        Integer userId = user.getUserId();
        List<RepairFormDto> all = service.findByRepairman(userId, perPageSize, offset, sortField, sortDir);
        CommandUtility.getRepairFomListPaginatedAddSessionAttributes(request, perPageSize, currentPage, service, all);
    }
}
