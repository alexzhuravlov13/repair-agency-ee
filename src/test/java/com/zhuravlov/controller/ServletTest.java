package com.zhuravlov.controller;

import com.zhuravlov.db.DbUtil;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class ServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        Servlet servlet = new Servlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        ServletConfig config = mock(ServletConfig.class);

        String path = "path";
        when(request.getRequestURI()).thenReturn(path);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        servlet.doGet(request, response);
        servlet.doPost(request, response);
        verify(dispatcher, times(2)).forward(request, response);

    }


}