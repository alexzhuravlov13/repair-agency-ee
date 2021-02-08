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
            "INSERT INTO users_roles (user_entity_user_id, role) VALUES (?, ?)";

    private static final String SELECT_ALL_FROM_USERS_SQL =
            "SELECT u.user_id, u.first_name, u.last_name, u.email, u.password, u.amount, roles.role " +
                    "FROM users u " +
                    "LEFT JOIN users_roles roles on u.user_id = roles.user_entity_user_id;";

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


    @Override
    public UserEntity findById(int id) {
        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> list = new ArrayList<>();

        Map<Integer, UserEntity> userById = new HashMap<>();

        try (Connection connection = DbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_FROM_USERS_SQL)) {
            while (resultSet.next()) {
                UserEntity userEntity = mapUserFromRs(userById, resultSet);
                list.add(userEntity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private UserEntity mapUserFromRs(Map<Integer, UserEntity> userById, ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String password = resultSet.getString("password");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        String role = resultSet.getString("role");

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
        return userEntity;
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
