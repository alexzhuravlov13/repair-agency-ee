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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            //myResultSet.getObject( … , LocalDate.class )
            ps.setObject(3, form.getCreationDate());
            ps.setString(4, form.getDescription());
            ps.setString(5, form.getFeedback());
            //myResultSet.getObject( … , LocalDate.class )
            ps.setObject(6, form.getLastModifiedDate());
            ps.setBigDecimal(7, form.getPrice());
            ps.setString(8, form.getShortDescription());
            ps.setString(9, form.getStatus().name());
            ps.setInt(10, form.getAuthor().getUserId());
            ps.setInt(11, form.getRepairman().getUserId());
            int i = ps.executeUpdate();

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
        String query = Constants.SELECT_ALL_REPAIR_FORMS;
        query = query.replaceAll("LIMIT \\? OFFSET \\? ORDER BY \\?", "");
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
        return repairFormEntityList;
    }

    public List<RepairFormEntity> findAll(int limit, int offset) {
        String query = Constants.SELECT_ALL_REPAIR_FORMS.replaceAll(" ORDER BY \\?", "");
        List<RepairFormEntity> repairFormEntityList;

        Map<Integer, UserEntity> userByIdMap = new HashMap<>();
        Map<Integer, RepairFormEntity> repairFormByIdMap = new HashMap<>();

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);

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
            int repairman_id = resultSet.getInt("repairman_id");
            String repairmanEmail = resultSet.getString("repairman_email");
            String repairmanFirstName = resultSet.getString("repairman_first_name");
            String repairmanLastName = resultSet.getString("repairman_last_name");
            String repairmanPassword = resultSet.getString("repairman_password");
            BigDecimal repairmanAmount = resultSet.getBigDecimal("repairman_amount");
            String repairmanRole = resultSet.getString("repairman_role");

            UserEntity repairmanEntityById = userByIdMap.get(repairman_id);
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
            repairFormEntityById.setAuthor(authorById);
            repairFormEntityById.setRepairman(repairmanEntityById);

            repairFormByIdMap.put(repairFormEntityById.getId(), repairFormEntityById);

        }

        //TODO:stream
        for (Map.Entry<Integer, RepairFormEntity> entry : repairFormByIdMap.entrySet()) {
            RepairFormEntity repairFormEntity = entry.getValue();
            repairFormEntity.setAuthor(userByIdMap.get(repairFormEntity.getAuthor().getUserId()));
            repairFormEntity.setRepairman(userByIdMap.get(repairFormEntity.getRepairman().getUserId()));
        }

        repairFormEntityList = new ArrayList<>(repairFormByIdMap.values());
        totalForms = i;
        return repairFormEntityList;
    }

    @Override
    public RepairFormEntity update(RepairFormEntity entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    public int getTotalForms() {
        return totalForms;
    }
}
