package com.zhuravlov;

import com.zhuravlov.command.Command;
import com.zhuravlov.command.Login;
import com.zhuravlov.command.Register;
import com.zhuravlov.command.Registration;

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
        Enumeration<String> enumeration = req.getAttributeNames();
        ArrayList<String> attributeNames = Collections.list(enumeration);
        for (String attributeName : attributeNames) {
            System.out.println(attributeName);
        }

        HttpSession session = req.getSession();
        Enumeration<String> attributeNames1 = session.getAttributeNames();
        ArrayList<String> attributeNames3 = Collections.list(attributeNames1);
        for (String attributeName : attributeNames3) {
            System.out.println(attributeName);
        }

        System.out.println();
        String path = req.getRequestURI();
        System.out.println(path);
        path = path.replaceAll(".*/app/" , "");
        System.out.println(path);
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
