package com.zhuravlov.controller;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.ErrorCommand;
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
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;
import com.zhuravlov.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        //Todo:password encryption
        //Todo:log4j.
        //Todo:Код повинен містити коментарі документації (всі класи верхнього рівня, нетривіальні методи і конструктори).
        //Todo:unit tests 40%
        //Todo:Validation

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
        Integer code = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String message = (String) req.getAttribute("javax.servlet.error.message");
        String exception_type = (String) req.getAttribute("javax.servlet.error.exception_type");
        String exception = (String) req.getAttribute("javax.servlet.error.exception");

        req.getSession().setAttribute("errorType", exception_type);
        req.getSession().setAttribute("errorMessage", message);
        req.getSession().setAttribute("exception", exception);
        req.getSession().setAttribute("errorCode", code);

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

    private void create() {
        UserService userService = new UserService();
        UserEntity admin = userService.findByEmail("admin@gmail.com");
        UserEntity user = userService.findByEmail("user666@gmail.com");

        RepairFormService service = new RepairFormService();

        RepairFormEntity entity = new RepairFormBuilder()
                .setAuthor(admin)
                .setCar("Car")
                .setDescription("descr")
                .setShortDescription("short")
                .setFeedback("feedback")
                .setStatus(Status.READY)
                .setCreationDate(LocalDateTime.now())
                .setLastModifiedDate(LocalDateTime.now())
                .setRepairman(user)
                .build();

        service.create(entity);

        entity = new RepairFormBuilder()
                .setAuthor(user)
                .setCar("Car")
                .setDescription("descr")
                .setShortDescription("short")
                .setFeedback("feedback")
                .setStatus(Status.IN_PROGRESS)
                .setCreationDate(LocalDateTime.now())
                .setLastModifiedDate(LocalDateTime.now())
                .setRepairman(admin)
                .build();
        service.create(entity);
    }

    private void initCommandsMap() {
        commands.put("error", new ErrorCommand());
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
