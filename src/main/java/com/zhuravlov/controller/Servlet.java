package com.zhuravlov.controller;

import com.zhuravlov.controller.command.*;
import com.zhuravlov.controller.command.authorization.*;
import com.zhuravlov.controller.command.repairForm.CreateRepairFormCommand;
import com.zhuravlov.controller.command.repairForm.UserRepairFormListCommand;
import com.zhuravlov.controller.command.user.DeleteUserCommand;
import com.zhuravlov.controller.command.user.EditUserCommand;
import com.zhuravlov.controller.command.user.SaveUserCommand;
import com.zhuravlov.controller.command.user.UserListCommand;
import com.zhuravlov.db.DbUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        DbUtil.init();

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("error", new ErrorCommand());
        commands.put("login", new LoginPageCommand());
        commands.put("loginUser", new LoginCommand());
        commands.put("logout", new LogOutCommand());
        commands.put("registration", new RegistrationPageController());
        commands.put("register", new RegisterCommand());
        commands.put("admin/listUsers", new UserListCommand());
        commands.put("admin/listUsers/edit", new EditUserCommand());
        commands.put("admin/listUsers/delete", new DeleteUserCommand());
        commands.put("admin/saveEditedUser", new SaveUserCommand());
        commands.put("user/userRepairFormList", new UserRepairFormListCommand());
        commands.put("user/addRepairForm", new CreateRepairFormCommand());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getRequestURI();

        System.out.println("#SERVLET path:" + path);

        path = path.replaceAll(".*/app/", "");

        Command command = commands.getOrDefault(path,
                (r) -> "error");
        String page = command.execute(req);

        if (page.contains("redirect:")) {
            page = page.replace("redirect:", "/app");
            System.out.println("#SERVLET redirect to:" + page);
            resp.sendRedirect(page);
        } else {
            System.out.println("#SERVLET forward to:" + page);
            req.getRequestDispatcher(page).forward(req, resp);
        }

    }
}
