package com.zhuravlov.controller.command.user;

import com.zhuravlov.service.UserService;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserListCommandTest {

    @Test
    public void userListExecute() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        UserService userService = mock(UserService.class);
        when(userService.findAll()).thenReturn(new ArrayList<>());

        UserListCommand command = new UserListCommand(userService);
        UserListCommand command2 = new UserListCommand();
        String execute = command.execute(request);
        assertEquals("/admin_users_list.jsp", execute);
    }
}