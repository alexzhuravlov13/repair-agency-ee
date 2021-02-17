package com.zhuravlov.db.Dao;

import com.zhuravlov.db.Constants;
import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class RepairFormDaoImpl implements Dao<RepairFormEntity> {
    private int totalForms;

    //id, car, creation_date, rf_description, feedback, last_modified_date,
    // price, rf_s_description, rf_status, author_id, repairman_id

    @Override
    public RepairFormEntity create(RepairFormEntity form) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(Constants.INSERT_REPAIR_FORM, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, form.getId());
            ps.setString(2, form.getCar());
            ps.setObject(3, form.getCreationDate());
            ps.setString(4, form.getDescription());
            ps.setString(5, form.getFeedback());
            ps.setObject(6, form.getLastModifiedDate());
            ps.setBigDecimal(7, form.getPrice());
            ps.setString(8, form.getShortDescription());
            ps.setString(9, form.getStatus().name());
            ps.setInt(10, form.getAuthor().getUserId());
            if (form.getRepairman() != null) {
                ps.setInt(11, form.getRepairman().getUserId());
            } else {
                ps.setInt(11, 0);
            }
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    form.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating form failed, no ID obtained.");
                }
            }
            return form;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RepairFormEntity findById(int id) {
        return null;
    }

    @Override
    public List<RepairFormEntity> findAll() {
      /*  String query = Constants.SELECT_ALL_REPAIR_FORMS;
        query = query.replaceAll("LIMIT \\? OFFSET \\?", "");
        List<RepairFormEntity> repairFormEntityList = null;

        Map<Integer, UserEntity> userByIdMap = new HashMap<>();
        Map<Integer, RepairFormEntity> repairFormByIdMap = new HashMap<>();

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            repairFormEntityList = getRepairFormEntities(userByIdMap, repairFormByIdMap, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return repairFormEntityList;*/
        return null;
    }

    /*public List<RepairFormEntity> findAll(int limit, int offset) {
        String query = Constants.SELECT_ALL_REPAIR_FORMS;
        List<RepairFormEntity> repairFormEntityList;
        System.out.println(query);

        Map<Integer, UserEntity> userByIdMap = new HashMap<>();
        Map<Integer, RepairFormEntity> repairFormByIdMap = new HashMap<>();

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, "r.id");
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);

            repairFormEntityList = getRepairFormEntities(userByIdMap, repairFormByIdMap, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return repairFormEntityList;
    }*/

    public List<RepairFormEntity> findAll(int limit, int offset, String sortField, String sortDir) {
        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "ASC";
        }

        if (sortField == null || sortField.isEmpty()) {
            sortField = "r.id";
        }

        String query = Constants.SELECT_ALL_REPAIR_FORMS
                .replaceAll("ORDER BY \\?", "ORDER BY \\? " + sortDir);
        System.out.println(query);
        List<RepairFormEntity> repairFormEntityList;

        Map<Integer, UserEntity> userByIdMap = new HashMap<>();
        Map<Integer, RepairFormEntity> repairFormByIdMap = new LinkedHashMap<>();

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, sortField);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);


            repairFormEntityList = getRepairFormEntities(userByIdMap, repairFormByIdMap, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return repairFormEntityList;
    }

    private List<RepairFormEntity> getRepairFormEntities(Map<Integer, UserEntity> userByIdMap, Map<Integer, RepairFormEntity> repairFormByIdMap, PreparedStatement preparedStatement) throws SQLException {
        List<RepairFormEntity> repairFormEntityList;
        ResultSet resultSet = preparedStatement.executeQuery();
        int i = 0;
        while (resultSet.next()) {
            //id,car,creation_date,rf_description,feedback,last_modified_date,price,rf_s_description,rf_status,
            int id = resultSet.getInt("id");
            String car = resultSet.getString("car");
            LocalDateTime creationDate = resultSet.getObject("creation_date", LocalDateTime.class);
            String description = resultSet.getString("rf_description");
            String feedback = resultSet.getString("feedback");
            LocalDateTime lastModifiedDate = resultSet.getObject("last_modified_date", LocalDateTime.class);
            BigDecimal price = resultSet.getBigDecimal("price");
            String shortDescription = resultSet.getString("rf_s_description");
            Status status = Status.valueOf(resultSet.getString("rf_status"));
            i = resultSet.getInt("totalForms");

            RepairFormEntity repairFormEntityById = repairFormByIdMap.get(id);
            if (repairFormEntityById == null) {
                repairFormEntityById = new RepairFormBuilder()
                        .setId(id)
                        .setCar(car)
                        .setCreationDate(creationDate)
                        .setDescription(description)
                        .setFeedback(feedback)
                        .setLastModifiedDate(lastModifiedDate)
                        .setPrice(price)
                        .setShortDescription(shortDescription)
                        .setStatus(status)
                        .build();
            }

            // author_id,author_amount,author_email,author_first_name,author_last_name,author_password,author_role,
            int author_id = resultSet.getInt("author_id");
            String email = resultSet.getString("author_email");
            String firstName = resultSet.getString("author_first_name");
            String lastName = resultSet.getString("author_last_name");
            String password = resultSet.getString("author_password");
            BigDecimal amount = resultSet.getBigDecimal("author_amount");
            String role = resultSet.getString("author_role");
            UserEntity authorById = userByIdMap.get(author_id);

            if (authorById == null) {
                authorById = new UserEntityBuilder()
                        .setUserId(author_id)
                        .setEmail(email)
                        .setFirstName(firstName)
                        .setLastName(lastName)
                        .setPassword(password)
                        .setAmount(amount)
                        .build();
                userByIdMap.put(authorById.getUserId(), authorById);
            }
            authorById.addRole(Role.valueOf(role));

            // repairman_id,repairman_amount,repairman_email,repairman_first_name,repairman_last_name,repairman_password,repairman_role
            UserEntity repairmanEntityById = null;
            int repairman_id = resultSet.getInt("repairman_id");
            if (repairman_id != 0) {
                String repairmanEmail = resultSet.getString("repairman_email");
                String repairmanFirstName = resultSet.getString("repairman_first_name");
                String repairmanLastName = resultSet.getString("repairman_last_name");
                String repairmanPassword = resultSet.getString("repairman_password");
                BigDecimal repairmanAmount = resultSet.getBigDecimal("repairman_amount");
                String repairmanRole = resultSet.getString("repairman_role");

                repairmanEntityById = userByIdMap.get(repairman_id);
                if (repairmanEntityById == null) {
                    repairmanEntityById = new UserEntityBuilder()
                            .setUserId(repairman_id)
                            .setEmail(repairmanEmail)
                            .setFirstName(repairmanFirstName)
                            .setLastName(repairmanLastName)
                            .setPassword(repairmanPassword)
                            .setAmount(repairmanAmount)
                            .build();
                    userByIdMap.put(repairmanEntityById.getUserId(), repairmanEntityById);
                }
                repairmanEntityById.addRole(Role.valueOf(repairmanRole));
            }

            repairFormEntityById.setAuthor(authorById);
            repairFormEntityById.setRepairman(repairmanEntityById);

            repairFormByIdMap.put(repairFormEntityById.getId(), repairFormEntityById);

        }

        //TODO:stream
        for (Map.Entry<Integer, RepairFormEntity> entry : repairFormByIdMap.entrySet()) {
            RepairFormEntity repairFormEntity = entry.getValue();
            repairFormEntity.setAuthor(userByIdMap.get(repairFormEntity.getAuthor().getUserId()));
            if (repairFormEntity.getRepairman() != null) {
                repairFormEntity.setRepairman(userByIdMap.get(repairFormEntity.getRepairman().getUserId()));
            }
        }
        repairFormEntityList = new ArrayList<>(repairFormByIdMap.values());
        totalForms = i;
        return repairFormEntityList;
    }


    @Override
    public RepairFormEntity update(RepairFormEntity entity) {
        //SET price = ?, rf_status = ?, repairman_id = ?, last_modified_date = ? WHERE id = ?
        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(Constants.UPDATE_REPAIR_FORM);
            ps.setBigDecimal(1, entity.getPrice());
            ps.setString(2, entity.getStatus().name());
            ps.setInt(3, entity.getRepairman().getUserId());
            ps.setObject(4, LocalDateTime.now());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public int getTotalForms() {
        return totalForms;
    }


    public List<RepairFormEntity> findByUserId(Integer userId, int limit, int offset, String sortField, String sortDir) {
        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "ASC";
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "r.id";
        }

        String query = Constants.SELECT_USER_REPAIR_FORMS
                .replaceAll("ORDER BY \\?", "ORDER BY \\? " + sortDir);

        List<RepairFormEntity> repairFormEntityList;

        Map<Integer, UserEntity> userByIdMap = new HashMap<>();
        Map<Integer, RepairFormEntity> repairFormByIdMap = new LinkedHashMap<>();

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, sortField);
            preparedStatement.setInt(4, limit);
            preparedStatement.setInt(5, offset);


            repairFormEntityList = getRepairFormEntities(userByIdMap, repairFormByIdMap, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return repairFormEntityList;
    }

    public void saveFeedback(int id, String feedback) {
        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(Constants.SAVE_REVIEW);
            ps.setString(1, feedback);
            ps.setObject(2, LocalDateTime.now());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean writeOffFunds(int id, int authorId, Status status, int repairmanId, BigDecimal price) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement("SELECT amount FROM users WHERE users.user_id=" + authorId);
            resultSet = ps.executeQuery();
            BigDecimal amount = null;
            while (resultSet.next()) {
                amount = resultSet.getBigDecimal("amount");
            }
            if (amount.compareTo(price) < 0) {
                return false;
            }

            con.setAutoCommit(false);
            ps = con.prepareStatement(Constants.UPDATE_REPAIR_FORM);
            ps.setBigDecimal(1, price);
            ps.setString(2, status.name());
            ps.setInt(3, repairmanId);
            ps.setObject(4, LocalDateTime.now());
            ps.setInt(5, id);
            ps.executeUpdate();

            ps = con.prepareStatement("UPDATE users SET users.amount=? WHERE users.user_id=?");
            ps.setBigDecimal(1, amount.subtract(price));
            ps.setInt(2, authorId);
            ps.executeUpdate();

            ps = con.prepareStatement("SELECT amount FROM users WHERE users.user_id=" + authorId);
            resultSet = ps.executeQuery();
            amount = null;
            while (resultSet.next()) {
                amount = resultSet.getBigDecimal("amount");
            }
            if (amount.doubleValue() < 0) {
                con.rollback();
            } else {
                con.commit();
            }
            con.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if(con!=null && ps!=null && resultSet!=null){
                    resultSet.close();
                    ps.close();
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
