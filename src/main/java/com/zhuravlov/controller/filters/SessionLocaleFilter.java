package com.zhuravlov.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * Process localisation from request parameter, default lang set to english (en)
     * @param servletRequest processed request
     * @param servletResponse processed response
     * @param filterChain processed filter chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String sessionLangAttr = (String) req.getSession().getAttribute("lang");
        String reqLangPar = req.getParameter("lang");

        if (sessionLangAttr == null && reqLangPar == null) {
            req.getSession().setAttribute("lang", "en");
        }

        if (reqLangPar != null && !reqLangPar.equals(sessionLangAttr)) {
            req.getSession().setAttribute("lang", reqLangPar);
        }

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
