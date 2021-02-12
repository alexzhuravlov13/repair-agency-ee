package com.zhuravlov.db.Dao;

import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements Dao<UserEntity> {
    private static final String INSERT_USER_SQL =
            "INSERT INTO users (first_name, last_name, email, password, amount) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_USER_ROLE_SQL =
            "INSERT INTO users_roles (user_id, user_role) VALUES (?, ?)";

    private static final String SELECT_ALL_FROM_USERS_SQL =
            "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, u.amount, roles.user_role " +
                    "FROM users u " +
                    "LEFT JOIN users_roles roles on u.user_id = roles.user_id";

    private static final String SELECT_USER_BY_ID_SQL =
            "SELECT users.user_id, users.first_name, users.last_name, users.email, " +
                    "users.password, users.amount, users_roles.user_role " +
                    "FROM users " +
                    "JOIN users_roles on users.user_id = users_roles.user_id " +
                    "WHERE users.user_id= ?";

    private static final String SELECT_USER_BY_EMAIL_SQL =
            "SELECT users.user_id, users.first_name, users.last_name, users.email, " +
                    "users.password, users.amount, users_roles.user_role " +
                    "FROM users " +
                    "JOIN users_roles on users.user_id = users_roles.user_id " +
                    "WHERE users.email= ?";

    @Override
    public UserEntity create(UserEntity userEntity) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userEntity.getFirstName());
            ps.setString(2, userEntity.getLastName());
            ps.setString(3, userEntity.getEmail());
            ps.setString(4, userEntity.getPassword());
            ps.setBigDecimal(5, userEntity.getAmount());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userEntity.setUserId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            insertUserRoles(userEntity, connection);
        } catch (SQLException e) {
            //TODO:what do we return?
            e.printStackTrace();
            return null;
        }

        return userEntity;
    }

    private void insertUserRoles(UserEntity userEntity, Connection connection) throws SQLException {
        PreparedStatement ps;
        connection.setAutoCommit(false);
        ps = connection.prepareStatement(INSERT_USER_ROLE_SQL);
        for (Role role : userEntity.getRoles()) {
            ps.setInt(1, userEntity.getUserId());
            ps.setString(2, String.valueOf(role));
            ps.addBatch();
        }
        ps.executeBatch();
        connection.commit();
    }

    public UserEntity findByEmail(String email) {
        UserEntity user = null;
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_EMAIL_SQL);
            ps.setString(1, email);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (user == null) {
                    user = mapUserFromRsByEmail(email, resultSet);
                }
                String role = resultSet.getString("user_role");
                user.addRole(Role.valueOf(role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public UserEntity findById(int id) {
        UserEntity user = null;
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SELECT_USER_BY_ID_SQL);
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (user == null) {
                    user = mapUserFromRsById(id, resultSet);
                }
                String role = resultSet.getString("user_role");
                user.addRole(Role.valueOf(role));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private UserEntity mapUserFromRsById(int id, ResultSet resultSet) throws SQLException {
        UserEntity user;
        String email = resultSet.getString("email");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String password = resultSet.getString("password");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        user = new UserEntityBuilder()
                .setUserId(id)
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setAmount(amount)
                .build();
        return user;
    }

    private UserEntity mapUserFromRsByEmail(String email, ResultSet resultSet) throws SQLException {
        UserEntity user;
        int id = resultSet.getInt("user_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String password = resultSet.getString("password");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        user = new UserEntityBuilder()
                .setUserId(id)
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setAmount(amount)
                .build();
        return user;
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> list = new ArrayList<>();

        Map<Integer, UserEntity> userById = new HashMap<>();

        try (Connection connection = DbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_FROM_USERS_SQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String email = resultSet.getString("email");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password = resultSet.getString("password");
                BigDecimal amount = resultSet.getBigDecimal("amount");
                String role = resultSet.getString("user_role");

                UserEntity userEntity = userById.get(id);

                if (userEntity == null) {
                    userEntity = new UserEntityBuilder()
                            .setUserId(id)
                            .setEmail(email)
                            .setFirstName(firstName)
                            .setLastName(lastName)
                            .setPassword(password)
                            .setAmount(amount)
                            .build();
                    userById.put(userEntity.getUserId(), userEntity);
                }
                userEntity.addRole(Role.valueOf(role));
            }
            list.addAll(userById.values());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public UserEntity update(UserEntity entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }


}
