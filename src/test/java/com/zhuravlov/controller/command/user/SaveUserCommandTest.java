package com.zhuravlov.controller.command.user;

import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveUserCommandTest {

    @Test
    public void saveUserExecute() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        UserService userService = mock(UserService.class);
        when(userService.update(any(UserEntity.class))).thenReturn(new UserEntity());

        SaveUserCommand command = new SaveUserCommand(userService);
        SaveUserCommand comman2 = new SaveUserCommand();
        String execute = command.execute(request);
        assertEquals("/admin_edit_user.jsp", execute);

        when(request.getParameter("userId")).thenReturn("1");
        when(request.getParameter("amount")).thenReturn("33.0");
        when(request.getParameter("firstName")).thenReturn("firstName");
        when(request.getParameter("lastName")).thenReturn("lastName");
        when(request.getParameter("email")).thenReturn("email@email.com");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameterValues("roles")).thenReturn(new String[]{"USER"});

        execute = command.execute(request);
        assertEquals("redirect:/admin/listUsers", execute);
    }
}