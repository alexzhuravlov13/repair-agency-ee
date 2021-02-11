package com.zhuravlov.filters;

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

    private void handleByRole(HttpServletRequest request, HttpServletResponse response, Set<Role> roles, String requestURL) throws IOException, ServletException {
        System.out.println("Handle by role " + roles);
        String homeDir = "";

        if (roles == null || roles.contains(Role.GUEST)) {
            if (requestURL.contains("/admin") || requestURL.contains("/user")) {
                System.out.println("Send redirect to login");
                request.getRequestDispatcher("/app/login").forward(request, response);
                //response.sendRedirect("redirect:/app/login");
            }
        } else if (requestURL.contains("/login") || requestURL.contains("/registration")) {
            if (roles.contains(Role.ADMIN)) {
                System.out.println("Send redirect to admin");
                homeDir = "/app/admin/listUsers";
            } else if (roles.contains(Role.USER)) {
                System.out.println("Send redirect to user");
                homeDir = "/app/user/userRepairFormList";
            }
            request.getRequestDispatcher(homeDir).forward(request, response);
            //response.sendRedirect(homeDir);
        } else if (requestURL.contains("/admin")){
            if (roles.contains(Role.USER)) {
                System.out.println("Send redirect to user");
                homeDir = "/app/user/userRepairFormList";
            }
            request.getRequestDispatcher(homeDir).forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
