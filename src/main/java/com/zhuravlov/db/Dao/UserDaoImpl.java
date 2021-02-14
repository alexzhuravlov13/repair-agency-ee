package com.zhuravlov.db.Dao;

import com.zhuravlov.db.Constants;
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

    @Override
    public UserEntity create(UserEntity userEntity) {
        try (Connection connection = DbUtil.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(Constants.INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
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
            connection.setAutoCommit(true);
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
        ps = connection.prepareStatement(Constants.INSERT_USER_ROLE_SQL);
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
            PreparedStatement ps = connection.prepareStatement(Constants.SELECT_USER_BY_EMAIL_SQL);
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
            PreparedStatement ps = connection.prepareStatement(Constants.SELECT_USER_BY_ID_SQL);
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

    @Override
    public List<UserEntity> findAll() {
        List<UserEntity> list = new ArrayList<>();

        Map<Integer, UserEntity> userById = new HashMap<>();

        try (Connection connection = DbUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(Constants.SELECT_ALL_FROM_USERS_SQL)) {
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
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Constants.UPDATE_USER_BY_ID);
            ps.setBigDecimal(1, entity.getAmount());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getFirstName());
            ps.setString(4, entity.getLastName());
            ps.setString(5, entity.getPassword());
            ps.setInt(6, entity.getUserId());
            int i = ps.executeUpdate();
            System.out.println(i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public boolean updateRoles(UserEntity user) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DbUtil.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(Constants.DELETE_USER_ROLES_BY_ID);
            ps.setInt(1, user.getUserId());
            ps.execute();
            insertUserRoles(user, con);
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null && ps != null) {
                    con.setAutoCommit(true);
                    ps.close();
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Constants.DELETE_USER_BY_ID);
            ps.setInt(1, id);
            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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


}
