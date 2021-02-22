package com.zhuravlov.controller.command;

import com.zhuravlov.controller.command.authorization.RegisterCommand;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.service.RepairFormService;
import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

public class CommandUtility {
    private static final Logger log = Logger.getLogger(CommandUtility.class);

    public static void setUserRoles(HttpServletRequest request,
                                    Set<Role> roles, String name) {
        HttpSession session = request.getSession();
        session.setAttribute("userName", name);
        session.setAttribute("roles", roles);
    }

    public static boolean checkUserIsLoggedOrLogin(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if (loggedUsers.stream().anyMatch(userName::equals)) {
            return true;
        }

        loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static String getSortField(HttpServletRequest req) {
        Map<String, String> sortFieldValueAndColumnName = new HashMap<>();
        sortFieldValueAndColumnName.put("creationDate", "r.creation_date");
        sortFieldValueAndColumnName.put("status", "r.rf_status");
        sortFieldValueAndColumnName.put("price", "r.price");
        String sortField = req.getParameter("sortField");
        if (sortField == null) {
            return "";
        }
        return sortFieldValueAndColumnName.get(sortField);

    }

    public static void getRepairFomListPaginatedAddSessionAttributes(HttpServletRequest request, int perPageSize, int currentPage, RepairFormService service, List all) {
        int formsCount = service.getFormsCount();

        int totalPages = formsCount / perPageSize;
        if (formsCount % perPageSize != 0) {
            totalPages = formsCount / perPageSize + 1;
        }

        request.setAttribute("userAmountForList", service.getAmount().toString());
        request.setAttribute("repairForms", all);
        request.setAttribute("perPageSize", perPageSize);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
    }

    public static boolean isValidated(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static String hashPass(String pass, String email) {
        try {
            byte[] salt = email.getBytes();
            KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f;
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            return enc.encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error(e.getStackTrace());
            return "";
        }
    }


}
