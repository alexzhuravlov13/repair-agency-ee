package com.zhuravlov.controller.command;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ErrorCommandTest {

    @Test
    public void execute() {
        ErrorCommand errorCommand = new ErrorCommand();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        assertEquals("/error.jsp", errorCommand.execute(request));
    }
}