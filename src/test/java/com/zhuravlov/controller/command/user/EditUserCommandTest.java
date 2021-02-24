package com.zhuravlov.controller.command.user;

import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EditUserCommandTest {

    @Test
    public void editUserExecute() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        when(userService.findById(anyInt())).thenReturn(new UserEntity());

        EditUserCommand command = new EditUserCommand(userService);
        EditUserCommand command2 = new EditUserCommand();
        when(request.getParameter("userId")).thenReturn("1");
        when(request.getSession()).thenReturn(session);

        String execute = command.execute(request);
        assertEquals("/admin_edit_user.jsp", execute);
    }
}