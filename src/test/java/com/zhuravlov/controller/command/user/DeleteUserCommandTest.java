package com.zhuravlov.controller.command.user;

import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteUserCommandTest {

    @Test
    public void execute() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        UserService userService = mock(UserService.class);
        when(userService.delete(anyInt())).thenReturn(true);

        DeleteUserCommand command = new DeleteUserCommand(userService);
        DeleteUserCommand command2 = new DeleteUserCommand();
        when(request.getParameter("userId")).thenReturn("1");

        String execute = command.execute(request);
        assertEquals("redirect:/admin/listUsers", execute);

    }
}