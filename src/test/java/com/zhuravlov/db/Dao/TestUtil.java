package com.zhuravlov.db.Dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zhuravlov.controller.command.Command;
import com.zhuravlov.controller.command.CommandUtility;
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
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TestUtil {
    private static final Map<String, Command> commands = new HashMap<>();

    public static boolean executeDBScripts(String aSQLScriptFilePath, Statement stmt) throws IOException, SQLException {
        boolean isScriptExecuted = false;
        try {
            BufferedReader in = new BufferedReader(new FileReader(aSQLScriptFilePath));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = in.readLine()) != null) {
                sb.append(str).append("\n ");
            }
            in.close();
            stmt.executeUpdate(sb.toString());
            isScriptExecuted = true;
        } catch (Exception e) {
            System.err.println("Failed to Execute" + aSQLScriptFilePath + ". The error is" + e.getMessage());
        } finally {
            stmt.close();
        }
        return isScriptExecuted;
    }

    public static void initH2() {
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
        config.setConnectionTestQuery("VALUES 1");
        config.addDataSourceProperty("URL", "jdbc:h2:~/test");
        config.addDataSourceProperty("user", "sa");
        config.addDataSourceProperty("password", "");
        HikariDataSource ds = new HikariDataSource(config);
        DbUtil.setDataSource(ds);
    }

    public static UserEntity getUser() {
        String email = "email@email.com";
        String password = "password";
        return UserEntityBuilder.getInstance()
                .setFirstName("first")
                .setLastName("last")
                .setEmail(email)
                .setPassword(CommandUtility.hashPass(password, email))
                .setRoles(new HashSet<>(Collections.singletonList(Role.USER)))
                .build();
    }

    public static RepairFormEntity getRepairForm() {
        return RepairFormBuilder.getInstance()
                .setStatus(Status.NEW)
                .setDescription("descr")
                .setShortDescription("sh_descr")
                .setCar("car")
                .setCreationDate(LocalDateTime.now())
                .build();
    }

    public static void initCommandsMap() {
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

    public static Command getCommand(String path) {
        return commands.get(path);
    }

    public static Map<String, Command> getCommands() {
        return commands;
    }
}
