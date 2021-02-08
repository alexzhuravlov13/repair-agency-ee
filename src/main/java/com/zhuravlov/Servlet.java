package com.zhuravlov;

import com.zhuravlov.command.Command;
import com.zhuravlov.command.Login;
import com.zhuravlov.command.Register;
import com.zhuravlov.command.Registration;
import com.zhuravlov.db.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    @Override
    public void init() throws ServletException {
        DbUtil.init();

        commands.put("login", new Login());
        commands.put("registration", new Registration());
        commands.put("register", new Register());
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

        path = path.replaceAll(".*/app/" , "");

        Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp)");
        String page = command.execute(req);
        if(page.contains("redirect:")){
            resp.sendRedirect(page.replace("redirect:", "/api"));
        }else {
            req.getRequestDispatcher(page).forward(req, resp);
        }

    }
}
