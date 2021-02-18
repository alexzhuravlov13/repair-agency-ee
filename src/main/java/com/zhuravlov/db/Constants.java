package com.zhuravlov.db;

public class Constants {
    public static final String INSERT_USER_SQL =
            "INSERT INTO users (first_name, last_name, email, password, amount) VALUES (?, ?, ?, ?, ?)";

    public static final String INSERT_USER_ROLE_SQL =
            "INSERT INTO users_roles (user_id, role) VALUES (?, ?)";

    public static final String SELECT_ALL_FROM_USERS_SQL =
            "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, u.amount, roles.role " +
                    "FROM users u " +
                    "LEFT JOIN users_roles roles on u.user_id = roles.user_id";

    public static final String SELECT_ALL_REPAIRMANS =
            "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, u.amount, users_roles.role " +
                    "FROM users u " +
                    "LEFT JOIN users_roles on u.user_id = users_roles.user_id " +
                    "WHERE users_roles.role='repairman'";

    public static final String SELECT_USER_BY_ID_SQL =
            "SELECT users.user_id, users.first_name, users.last_name, users.email, " +
                    "users.password, users.amount, users_roles.role " +
                    "FROM users " +
                    "JOIN users_roles on users.user_id = users_roles.user_id " +
                    "WHERE users.user_id= ?";

    public static final String SELECT_USER_BY_EMAIL_SQL =
            "SELECT users.user_id, users.first_name, users.last_name, users.email, " +
                    "users.password, users.amount, users_roles.role " +
                    "FROM users " +
                    "JOIN users_roles on users.user_id = users_roles.user_id " +
                    "WHERE users.email= ?";

    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id=?";

    public static final String UPDATE_USER_BY_ID = "UPDATE users " +
            "SET amount = ?, email = ?, first_name = ?, last_name = ?, password = ? WHERE user_id = ?";

    public static final String DELETE_USER_ROLES_BY_ID = "DELETE FROM users_roles WHERE user_id=?";

    public static final String INSERT_REPAIR_FORM =
            "INSERT INTO repair_forms" +
                    "(id, car, creation_date, rf_description, feedback, last_modified_date, price, rf_s_description, rf_status, author_id, repairman_id)" +
                    "VALUES" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_REPAIR_FORM_BY_ID =
            "SELECT r.id, r.car, r.creation_date, r.rf_description, r.feedback, r.last_modified_date, r.price, r.rf_s_description, r.rf_status, " +
                    "u.user_id as author_id, u.amount as author_amount, u.email as author_email, u.first_name as author_first_name, u.last_name as author_last_name, u.password as author_password, " +
                    "author_roles.role as author_role," +
                    "repairman.user_id as repairman_id, repairman.amount as repairman_amount, repairman.email as repairman_email, repairman.first_name as repairman_first_name, repairman.last_name as repairman_last_name, repairman.password as repairman_password, " +
                    "repairman_roles.role as repairman_role " +
                    "FROM repair_forms r " +
                    "JOIN users u ON u.user_id = r.author_id " +
                    "JOIN users_roles author_roles ON author_roles.user_id = r.author_id " +
                    "JOIN users repairman ON repairman.user_id = r.repairman_id " +
                    "JOIN users_roles repairman_roles on repairman_roles.user_id = r.repairman_id " +
                    "WHERE r.id=?";

    public static final String SELECT_ALL_REPAIR_FORMS =
            "SELECT r.id, r.car, r.creation_date, r.rf_description, r.feedback, r.last_modified_date, r.price, r.rf_s_description, r.rf_status, " +
                    "u.user_id as author_id, u.amount as author_amount, u.email as author_email, u.first_name as author_first_name, u.last_name as author_last_name, u.password as author_password, " +
                    "author_roles.role as author_role, " +
                    "repairman.user_id as repairman_id, repairman.amount as repairman_amount, repairman.email as repairman_email, repairman.first_name as repairman_first_name, repairman.last_name as repairman_last_name, repairman.password as repairman_password, " +
                    "repairman_roles.role as repairman_role, " +
                    "(SELECT COUNT(*) FROM repair_forms) AS totalForms " +
                    "FROM repair_forms r " +
                    "JOIN users u ON u.user_id = r.author_id " +
                    "JOIN users_roles author_roles ON author_roles.user_id = r.author_id " +
                    "LEFT JOIN users repairman ON repairman.user_id = r.repairman_id " +
                    "LEFT JOIN users_roles repairman_roles on repairman_roles.user_id = r.repairman_id " +
                    "GROUP BY r.id " +
                    "ORDER BY ? LIMIT ? OFFSET ? ";

    public static final String SELECT_USER_REPAIR_FORMS =
            "SELECT r.id, r.car, r.creation_date, r.rf_description, r.feedback, r.last_modified_date, r.price, r.rf_s_description, r.rf_status, " +
                    "u.user_id as author_id, u.amount as author_amount, u.email as author_email, u.first_name as author_first_name, u.last_name as author_last_name, u.password as author_password, " +
                    "author_roles.role as author_role, " +
                    "repairman.user_id as repairman_id, repairman.amount as repairman_amount, repairman.email as repairman_email, repairman.first_name as repairman_first_name, repairman.last_name as repairman_last_name, repairman.password as repairman_password, " +
                    "repairman_roles.role as repairman_role, " +
                    "(SELECT COUNT(*) FROM repair_forms WHERE repair_forms.author_id = ?) AS totalForms " +
                    "FROM repair_forms r " +
                    "JOIN users u ON u.user_id = r.author_id " +
                    "JOIN users_roles author_roles ON author_roles.user_id = r.author_id " +
                    "LEFT JOIN users repairman ON repairman.user_id = r.repairman_id " +
                    "LEFT JOIN users_roles repairman_roles on repairman_roles.user_id = r.repairman_id " +
                    "WHERE r.author_id = ? " +
                    "GROUP BY r.id " +
                    "ORDER BY ? LIMIT ? OFFSET ? ";

    public static final String SELECT_REPAIRMAN_REPAIR_FORMS =
            "SELECT r.id, r.car, r.creation_date, r.last_modified_date, r.price, r.rf_s_description, r.rf_status, " +
                    "u.user_id as author_id, u.first_name as author_first_name, u.last_name as author_last_name, " +
                    "repairman.user_id as repairman_id, repairman.first_name as repairman_first_name, repairman.last_name as repairman_last_name, " +
                    "(SELECT COUNT(*) FROM repair_forms WHERE repair_forms.repairman_id = ?) AS totalForms " +
                    "FROM repair_forms r " +
                    "JOIN users u ON u.user_id = r.author_id " +
                    "LEFT JOIN users repairman ON repairman.user_id = r.repairman_id " +
                    "WHERE r.repairman_id = ? " +
                    "GROUP BY r.id " +
                    "ORDER BY ? LIMIT ? OFFSET ? ";

    public final static String SAVE_REVIEW =
            "UPDATE repair_forms SET feedback = ?, last_modified_date = ? WHERE id = ?";

    public static final String UPDATE_REPAIR_FORM =
            "UPDATE repair_forms " +
            "SET price = ?, rf_status = ?, repairman_id = ?, last_modified_date = ? WHERE id = ?";
}
