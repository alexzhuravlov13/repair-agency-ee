package com.zhuravlov.controller.command;

import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.service.RepairFormService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class CommandUtility {
    public static void setUserRoles(HttpServletRequest request,
                                    Set<Role> roles, String name) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();

        context.setAttribute("userName", name);
        session.setAttribute("roles", roles);
        session.setAttribute("roleAdmin", Role.ADMIN);
        session.setAttribute("roleManager", Role.MANAGER);
        session.setAttribute("roleRepairman", Role.REPAIRMAN);
        session.setAttribute("statusReady", Status.READY);
    }

    public static boolean checkUserIsLoggedOrLogin(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if (loggedUsers.stream().anyMatch(userName::equals)) {
            return true;
        }

        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static String getSortField(HttpServletRequest req) {
        Map<String, String> sortFieldValueAndColumnName = new HashMap<>();
        sortFieldValueAndColumnName.put("creationDate", "r.creation_date");
        sortFieldValueAndColumnName.put("status", "r.rf_status");
        sortFieldValueAndColumnName.put("price", "r.price");
        String sortField = req.getParameter("sortField");
        if (sortField == null) {
            return "";
        }
        return sortFieldValueAndColumnName.get(sortField);

    }

    public static void getRepairFomListPaginatedAddSessionAttributes(HttpSession session, int perPageSize, int currentPage, RepairFormService service, List all) {
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
