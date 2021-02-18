package com.zhuravlov.controller.filters;

import com.zhuravlov.model.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Set<Role> roles = (Set<Role>) session.getAttribute("roles");

        String requestURL = request.getRequestURL().toString();

        System.out.println("#AUTH roles:" + roles);
        System.out.println("#AUTH requestURL:" + requestURL);

        handleByRole(request, response, roles, requestURL);

        filterChain.doFilter(request, response);
    }

    private void handleByRole(HttpServletRequest request, HttpServletResponse response, Set<Role> roles, String requestURL) throws ServletException, IOException {
        System.out.println("Handle by role " + roles);
        String destination = "";

        if (roles == null || roles.contains(Role.GUEST)) {
            if (requestURL.contains("/admin") || requestURL.contains("/user") || requestURL.contains("/manager") || requestURL.contains("/repairman")) {
                System.out.println("Send redirect to login");
                destination = "/app/login";
                request.getRequestDispatcher(destination).forward(request, response);
                //response.sendRedirect("redirect:/app/login");
            }
        } else if (roles.contains(Role.ADMIN)) {
            if (requestURL.contains("/login") || requestURL.contains("/registration")) {
                System.out.println("Send redirect to admin");
                destination = "/app/admin/listUsers";
                request.getRequestDispatcher(destination).forward(request, response);
            }
        } else if (roles.contains(Role.MANAGER)) {
            if (requestURL.contains("/login") || requestURL.contains("/registration")
                    || requestURL.contains("/admin")) {
                System.out.println("Send redirect to manager");
                destination = "/app/manager/listUsers";
                request.getRequestDispatcher(destination).forward(request, response);
            }

        } else if (roles.contains(Role.REPAIRMAN)) {
            System.out.println("Send redirect to REPAIRMAN");
        } else if (roles.contains(Role.USER)) {
            if (requestURL.contains("/login") || requestURL.contains("/registration")
                    || requestURL.contains("/admin") || requestURL.contains("/manager")) {
                System.out.println("Send redirect to user");
                destination = "/app/repairman/repairmanRepairFormList";
                request.getRequestDispatcher(destination).forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
