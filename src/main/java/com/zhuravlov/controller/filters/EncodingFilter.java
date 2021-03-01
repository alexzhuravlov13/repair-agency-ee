package com.zhuravlov.controller.filters;


import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    /**
     * Set encoding parameters
     * @param servletRequest processed request
     * @param servletResponse processed response
     * @param filterChain processed filter chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
