package com.zhuravlov;

import com.zhuravlov.command.*;
import com.zhuravlov.db.DbUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        DbUtil.init();

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("login", new LoginPageCommand());
        commands.put("loginUser", new LoginCommand());
        commands.put("logout", new LogOutCommand());
        commands.put("registration", new RegistrationPageController());
        commands.put("register", new RegisterCommand());
        commands.put("admin/listUsers", new UserList());
        commands.put("admin/listUsers/edit", new EditUser());
        commands.put("user/userRepairFormList", new UserRepairFormListCommand());

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

        System.out.println("Path:" + path);

        path = path.replaceAll(".*/app/" , "");

        System.out.println("Path:" + path);

        Command command = commands.getOrDefault(path ,
                (r)->"/login.jsp)");
        String page = command.execute(req);

        System.out.println("Path:" + page);

        if(page.contains("redirect:")){
            resp.sendRedirect(page.replace("redirect:", "/app"));
        }else {
            req.getRequestDispatcher(page).forward(req, resp);
        }

    }
}
