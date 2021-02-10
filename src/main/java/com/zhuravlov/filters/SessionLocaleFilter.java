package com.zhuravlov.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

       if(req.getSession().getAttribute("lang") ==null){
           req.getSession().setAttribute("lang", "en");
       }

        String langValue = req.getParameter("lang");

        if (langValue != null) {
            req.getSession().setAttribute("lang", langValue);
        }

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
