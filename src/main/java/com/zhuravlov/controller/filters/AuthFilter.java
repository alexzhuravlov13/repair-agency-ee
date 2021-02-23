package com.zhuravlov.controller.filters;

import com.zhuravlov.model.entity.Role;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class AuthFilter implements Filter {
    public static final Logger log = Logger.getLogger(AuthFilter.class);

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

        handleByRole(request, response, roles, requestURL);

        filterChain.doFilter(request, response);
    }

    private void handleByRole(HttpServletRequest request, HttpServletResponse response, Set<Role> roles, String requestURL) throws ServletException, IOException {
        log.info("#AUTH roles:" + roles);
        log.info("#AUTH requestURL:" + requestURL);

        log.info("Handle by role " + roles);
        String destination = "";

        if (roles == null || roles.contains(Role.GUEST)) {
            if (requestURL.contains("/admin") || requestURL.contains("/user") || requestURL.contains("/manager") || requestURL.contains("/repairman")) {
                log.info("Send redirect to login");
                destination = "/app/login";
                request.getRequestDispatcher(destination).forward(request, response);
            }
        } else if (roles.contains(Role.ADMIN)) {
            if (requestURL.contains("/login") || requestURL.contains("/registration")) {
                log.info("Send redirect to admin");
                destination = "/app/admin/listUsers";
                request.getRequestDispatcher(destination).forward(request, response);
            }
        } else if (roles.contains(Role.MANAGER)) {
            if (requestURL.contains("/login") || requestURL.contains("/registration")
                    || requestURL.contains("/admin") || requestURL.contains("/repairman")) {
                log.info("Send redirect to manager");
                destination = "/app/manager/listUsers";
                request.getRequestDispatcher(destination).forward(request, response);
            }
        } else if (roles.contains(Role.REPAIRMAN)) {
            if (requestURL.contains("/login") || requestURL.contains("/registration")
                    || requestURL.contains("/admin") || requestURL.contains("/manager")) {
                log.info("Send redirect to REPAIRMAN");
                destination = "/app/manager/listUsers";
                request.getRequestDispatcher(destination).forward(request, response);
            }
        } else if (roles.contains(Role.USER)) {
            if (requestURL.contains("/login") || requestURL.contains("/registration")
                    || requestURL.contains("/admin") || requestURL.contains("/manager")
                    || requestURL.contains("/repairman")) {
                log.info("Send redirect to user");
                destination = "/app/repairman/repairmanRepairFormList";
                request.getRequestDispatcher(destination).forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
