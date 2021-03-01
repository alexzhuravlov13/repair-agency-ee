package com.zhuravlov.controller.command;

import com.zhuravlov.model.dto.RepairFormDto;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import com.zhuravlov.service.RepairFormService;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;

/**
 * Class for commands utility methods
 */
public class CommandUtility {
    private static final Logger log = Logger.getLogger(CommandUtility.class);

    /**
     * Add username and roles of user to session
     * @param request processed request
     * @param roles roles of loggined user
     * @param name username of loggined user
     */
    public static void setUserRoles(HttpServletRequest request,
                                    Set<Role> roles, String name) {
        HttpSession session = request.getSession();
        session.setAttribute("userName", name);
        session.setAttribute("roles", roles);
    }

    /**
     * Check if user loggined by getting user from app context, if not - login, prevent second login
     * @param request processed request
     * @param userName user login
     * @return true if user was already loggined
     */
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

    /**
     * Get sort field in database by key
     * @param req processed request
     * @return sort field name in database
     */
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

    /**
     * Get paginated repairs form list
     * @see RepairFormDto
     * @see com.zhuravlov.model.entity.RepairFormEntity
     * @param request processed request
     * @param perPageSize amount of repair forms on page
     * @param currentPage current page
     * @param service repair forms service
     * @param all list of repair forms
     */
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

    /**
     *
     * @param request
     * @param role
     */
    public static void addListWithPaginationAndSorting(HttpServletRequest request, Role role) {
        HttpSession session = request.getSession();

        String sortDir = request.getParameter("sortDir");
        if (sortDir == null) {
            sortDir = "asc";
        }

        String sortField = CommandUtility.getSortField(request);
        addListWithPagination(request, session, sortField, sortDir, role);

        request.setAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }

    /**
     * Add repair form list with pagination
     * @see RepairFormDto
     * @see com.zhuravlov.model.entity.RepairFormEntity
     * @param request processed request
     * @param session user session
     * @param sortField field to sort repair form list
     * @param sortDir direction of sorting, asc or desc
     * @param role get repair form list by role
     */
    public static void addListWithPagination(HttpServletRequest request, HttpSession session, String sortField, String sortDir, Role role) {

        int page = 1;
        int perPageSize = 5;
        int currentPage = 1;

        String pagePar = request.getParameter("page");
        if (pagePar != null) {
            page = Integer.parseInt(pagePar);
            currentPage = page;
        }

        int offset = 0;

        if (page > 1) {
            offset = (page - 1) * perPageSize;
        }

        RepairFormService service = new RepairFormService();
        if (sortDir == null) {
            sortDir = "";
        }
        UserEntity user = (UserEntity) session.getAttribute("user");
        Integer userId = user.getUserId();
        List<RepairFormDto> all = null;

        if (role.equals(Role.USER)) {
            all = service.findByUserId(userId, perPageSize, offset, sortField, sortDir);
        } else if (role.equals(Role.REPAIRMAN)) {
            all = service.findByRepairman(userId, perPageSize, offset, sortField, sortDir);
        } else if (role.equals(Role.MANAGER)) {
            Status statusFilter = (Status) session.getAttribute("statusFilter");
            Integer repairmanFilter = (Integer) session.getAttribute("repairmanFilter");
            all = service.findAll(perPageSize, offset, sortField, sortDir, repairmanFilter, statusFilter);
        }

        CommandUtility.getRepairFomListPaginatedAddSessionAttributes(request, perPageSize, currentPage, service, all);
    }

    /**
     * Check String params for valid data (not null and not empty)
     * @param args list of strings to validate
     * @return false if one of strings is not valid
     */
    public static boolean isValidated(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Hashing password
     * @param pass user password would be hashed
     * @param email user email used as a salt
     * @return hashed password in string format
     */
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
            log.error(e.getMessage());
            return "";
        }
    }

    /**
     * Add user to session as attribute, get path of home page
     * @param request processed request
     * @param email user email (username)
     * @param user user entity that was logged in
     * @return home page by user role
     */
    public static String loginUserAndSendToHomePage(HttpServletRequest request, String email, UserEntity user) {
        Set<Role> userRoles = user.getRoles();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        CommandUtility.setUserRoles(request, userRoles, email);
        return getUserHomePage(userRoles);
    }

    /**
     * Get path of home page
     * @param userRoles list of user roles
     * @return home page by user role
     */
    public static String getUserHomePage(Set<Role> userRoles) {
        if (userRoles.contains(Role.ADMIN)) {
            return "redirect:/admin/listUsers";
        } else if (userRoles.contains(Role.MANAGER)) {
            return "redirect:/manager/home";
        } else if (userRoles.contains(Role.REPAIRMAN)) {
            return "redirect:/repairman/repairmanRepairFormList";
        } else if (userRoles.contains(Role.USER)) {
            return "redirect:/user/userRepairFormList";
        }
        return "redirect:/login";
    }

    /**
     * Add roles and statuses data for edit forms to session as attribute
     * @param request processed request
     */
    public static void initDataForEdit(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("roleAdmin", Role.ADMIN);
        session.setAttribute("roleManager", Role.MANAGER);
        session.setAttribute("roleRepairman", Role.REPAIRMAN);
        session.setAttribute("statusReady", Status.READY);
        session.setAttribute("statusCanceled", Status.CANCELED);
    }

    /**
     * Logout user by deleting them from app context user set and invalidate session
     * @param request processed request
     * @param session user Session
     * @param userName user login name
     */
    public static void logOut(HttpServletRequest request, HttpSession session, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.remove(userName);

        CommandUtility.setUserRoles(request, new HashSet<>(Collections.singletonList(Role.GUEST)),
                "Guest");

        request.getServletContext().setAttribute("userName", "");

        request.getSession().invalidate();
    }

    /**
     * Email validation
     * @param email user email in string format
     * @return true if email is in valid format
     */
    public static boolean isEmailCorrect(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Set filters for repair forms list
     * @param request processed request
     */
    public static void setFilters(HttpServletRequest request) {
        Status status = null;
        String statusPar = request.getParameter("status");
        if (CommandUtility.isValidated(statusPar)) {
            status = Status.valueOf(statusPar);
        }

        Integer repairmanId = null;
        String repairmanPar = request.getParameter("repairman");
        if (CommandUtility.isValidated(repairmanPar)) {
            repairmanId = Integer.parseInt(repairmanPar);
        }

        HttpSession session = request.getSession();
        session.setAttribute("statusFilter", status);
        session.setAttribute("repairmanFilter", repairmanId);
    }



}
