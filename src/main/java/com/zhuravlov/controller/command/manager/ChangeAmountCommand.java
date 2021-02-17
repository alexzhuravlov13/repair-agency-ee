package com.zhuravlov.controller.command.manager;

import com.zhuravlov.controller.command.Command;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class ChangeAmountCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(req.getParameter("amount")));
        UserEntity user = (UserEntity) session.getAttribute("editedUser");
        user.setAmount(user.getAmount().add(amount));

        UserService service = new UserService();
        service.update(user);
        return "redirect:/manager/listUsers";
    }
}
