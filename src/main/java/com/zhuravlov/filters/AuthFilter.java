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


        if (requestURL.equals("http://localhost:8081/")) {
            System.out.println("#AUTH: check /");
            if (roles == null || roles.contains(Role.GUEST)) {
                response.sendRedirect("/app/login");
            }
        }


        if (requestURL.contains("login")) {
            System.out.println("#AUTH: check login");
            if (roles != null && !roles.contains(Role.GUEST)) {
                request.getRequestDispatcher("/app/home").forward(request, response);
            }
        }


        if (requestURL.contains("home")) {
            if (roles != null) {
                System.out.println("#AUTH: check home");
                if (roles.contains(Role.ADMIN)) {
                    request.getRequestDispatcher("/app/admin/listUsers").forward(request, response);
                } else if (roles.contains(Role.USER)) {
                    request.getRequestDispatcher("/app/user/userRepairFormList").forward(request, response);
                }

            }
        }


        if (requestURL.contains("/admin")) {
            System.out.println("#AUTH: check /admin");
            if (roles == null || !roles.contains(Role.ADMIN)) {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }


        if (requestURL.contains("/user")) {
            System.out.println("#AUTH: check /user");
            if (roles == null || !roles.contains(Role.USER)) {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        }


        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }
}
