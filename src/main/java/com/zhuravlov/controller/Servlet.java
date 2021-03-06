package com.zhuravlov.controller;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.HomePageCommand;
import com.zhuravlov.controller.command.authorization.*;
import com.zhuravlov.controller.command.manager.*;
import com.zhuravlov.controller.command.repairForm.*;
import com.zhuravlov.controller.command.repairman.RepairmanEditRepairFormCommand;
import com.zhuravlov.controller.command.repairman.RepairmanRepairFormListCommand;
import com.zhuravlov.controller.command.repairman.RepairmanSaveRepairFormCommand;
import com.zhuravlov.controller.command.user.DeleteUserCommand;
import com.zhuravlov.controller.command.user.EditUserCommand;
import com.zhuravlov.controller.command.user.SaveUserCommand;
import com.zhuravlov.controller.command.user.UserListCommand;
import com.zhuravlov.db.DbUtil;
import org.apache.log4j.Logger;

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
    public static final Logger log = Logger.getLogger(Servlet.class);

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        DbUtil.setUrlFromProp();
        DbUtil.init();

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        initCommandsMap();
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

        log.info("#SERVLET path:" + path);

        path = path.replaceAll(".*/app/", "");

        Command command = commands.getOrDefault(path,
                (r) -> "/404.jsp");
        String page = command.execute(req);

        if (page.contains("redirect:")) {
            page = page.replace("redirect:", "/app");
            log.info("#SERVLET redirect to:" + page);
            resp.sendRedirect(page);
        } else {
            log.info("#SERVLET forward to:" + page);
            req.getRequestDispatcher(page).forward(req, resp);
        }

    }

    private void initCommandsMap() {
        commands.put("home", new HomePageCommand());
        commands.put("login", new LoginPageCommand());
        commands.put("loginUser", new LoginCommand());
        commands.put("logout", new LogOutCommand());
        commands.put("registration", new RegistrationPageController());
        commands.put("register", new RegisterCommand());

        commands.put("manager/listUsers", new ManagerUserListCommand());
        commands.put("manager/listUsers/changeAmount", new ChangeAmountPageCommand());
        commands.put("manager/listUsers/saveAmount", new ChangeAmountCommand());
        commands.put("manager/managerRepairFormList", new ManagerRepairFormListCommand());
        commands.put("manager/editRepairForm", new ManagerEditRepairFormCommand());
        commands.put("manager/saveRepairForm", new ManagerSaveRepairFormCommand());
        commands.put("manager/addRepairFormFilter", new AddRepairFormFilterCommand());
        commands.put("manager/clearRepairFormFilter", new ClearRepairFormFilterCommand());
        commands.put("manager/home", new ManagerHomePageCommand());

        commands.put("repairman/repairmanRepairFormList", new RepairmanRepairFormListCommand());
        commands.put("repairman/editRepairForm", new RepairmanEditRepairFormCommand());
        commands.put("repairman/saveRepairForm", new RepairmanSaveRepairFormCommand());

        commands.put("admin/listUsers", new UserListCommand());
        commands.put("admin/listUsers/edit", new EditUserCommand());
        commands.put("admin/listUsers/delete", new DeleteUserCommand());
        commands.put("admin/saveEditedUser", new SaveUserCommand());

        commands.put("user/userRepairFormList", new UserRepairFormListCommand());
        commands.put("user/addRepairFormPage", new CreateRepairFormPageCommand());
        commands.put("user/addRepairForm", new CreateRepairFormCommand());
        commands.put("user/viewRepairForm", new ViewRepairFormCommand());
        commands.put("user/reviewRepairForm", new ReviewRepairFormCommand());
        commands.put("user/saveReview", new SaveReviewCommand());
    }
}
