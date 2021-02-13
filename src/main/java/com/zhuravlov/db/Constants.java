package com.zhuravlov.db;

public class Constants {
    public static final String INSERT_USER_SQL =
            "INSERT INTO users (first_name, last_name, email, password, amount) VALUES (?, ?, ?, ?, ?)";

    public static final String INSERT_USER_ROLE_SQL =
            "INSERT INTO users_roles (user_id, user_role) VALUES (?, ?)";

    public static final String SELECT_ALL_FROM_USERS_SQL =
            "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, u.amount, roles.user_role " +
                    "FROM users u " +
                    "LEFT JOIN users_roles roles on u.user_id = roles.user_id";

    public static final String SELECT_USER_BY_ID_SQL =
            "SELECT users.user_id, users.first_name, users.last_name, users.email, " +
                    "users.password, users.amount, users_roles.user_role " +
                    "FROM users " +
                    "JOIN users_roles on users.user_id = users_roles.user_id " +
                    "WHERE users.user_id= ?";

    public static final String SELECT_USER_BY_EMAIL_SQL =
            "SELECT users.user_id, users.first_name, users.last_name, users.email, " +
                    "users.password, users.amount, users_roles.user_role " +
                    "FROM users " +
                    "JOIN users_roles on users.user_id = users_roles.user_id " +
                    "WHERE users.email= ?";

    public static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id=?";

    public static final String UPDATE_USER_BY_ID = "UPDATE users " +
            "SET amount = ?, email = ?, first_name = ?, last_name = ?, password = ? WHERE user_id = ?";

    public static final String DELETE_USER_ROLES_BY_ID = "DELETE FROM users_roles WHERE user_id=?";
}
