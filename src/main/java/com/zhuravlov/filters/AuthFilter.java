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

        System.out.println("#AUTH: before check /");
        if (requestURL.equals("http://localhost:8081/")) {
            if (roles == null || roles.contains(Role.GUEST)) {
                response.sendRedirect("/app/login");
                //request.getRequestDispatcher("login").forward(request, response);
                filterChain.doFilter(request, response);
                return;
            }
        }

        System.out.println("#AUTH: before check login");
        if (requestURL.contains("login")) {
            if (roles != null && !roles.contains(Role.GUEST)) {
                request.getRequestDispatcher("/home").forward(request, response);
                filterChain.doFilter(request, response);
                return;
            }
        }

        System.out.println("#AUTH: before check home");
        if (requestURL.contains("/home")) {
            if (roles != null) {
                if (roles.contains(Role.USER)) {
                    request.getRequestDispatcher("/user/userRepairFormList").forward(request, response);
                }
                filterChain.doFilter(request, response);
                return;
            }
        }

        System.out.println("#AUTH: before check admin");
        if (requestURL.contains("/admin")) {
            if (roles == null || !roles.contains(Role.ADMIN)) {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                filterChain.doFilter(request, response);
                return;
            }
        }

        System.out.println("#AUTH: before check user");
        if (requestURL.contains("/user")) {
            if (roles == null || !roles.contains(Role.USER)) {
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                filterChain.doFilter(request, response);
                return;
            }
        }


        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }
}
