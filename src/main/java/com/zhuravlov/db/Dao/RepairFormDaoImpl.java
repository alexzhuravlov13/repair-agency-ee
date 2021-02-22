package com.zhuravlov.db.Dao;

import com.zhuravlov.controller.Servlet;
import com.zhuravlov.db.Constants;
import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.builder.RepairFormDtoBuilder;
import com.zhuravlov.model.builder.UserEntityBuilder;
import com.zhuravlov.model.dto.RepairFormDto;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class RepairFormDaoImpl implements Dao<RepairFormEntity> {
    private int totalForms;
    private BigDecimal userAmount = BigDecimal.valueOf(0);
    public static final Logger log = Logger.getLogger(RepairFormDaoImpl.class);

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
            log.error(e.getStackTrace());
            return null;
        }
    }

    @Override
    public RepairFormEntity findById(int repairFormId) {

        Map<Integer, UserEntity> userByIdMap = new HashMap<>();
        Map<Integer, RepairFormEntity> repairFormByIdMap = new LinkedHashMap<>();

        RepairFormEntity repairFormEntityById = null;

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(Constants.SELECT_REPAIR_FORM_BY_ID);
            preparedStatement.setInt(1, repairFormId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                repairFormEntityById = repairFormByIdMap.get(repairFormId);
                if (repairFormEntityById == null) {
                    repairFormEntityById = getRepairFormEntity(repairFormId, resultSet);
                }

                int author_id = resultSet.getInt("author_id");

                UserEntity authorId = userByIdMap.get(author_id);
                String authorRole = resultSet.getString("author_role");

                if (authorId == null) {
                    authorId = getAuthorUserEntity(resultSet, author_id);
                    userByIdMap.put(authorId.getUserId(), authorId);
                }
                authorId.addRole(Role.valueOf(authorRole));

                UserEntity repairmanEntityById = null;
                int repairmanId = resultSet.getInt("repairman_id");
                String repairmanRole = resultSet.getString("repairman_role");

                if (repairmanId != 0) {
                    repairmanEntityById = userByIdMap.get(repairmanId);
                    if (repairmanEntityById == null) {
                        repairmanEntityById = getRepairmanUserEntity(resultSet, repairmanId);
                        userByIdMap.put(repairmanEntityById.getUserId(), repairmanEntityById);
                    }
                    repairmanEntityById.addRole(Role.valueOf(repairmanRole));
                }

                repairFormEntityById.setAuthor(authorId);
                repairFormEntityById.setRepairman(repairmanEntityById);

                repairFormByIdMap.put(repairFormEntityById.getId(), repairFormEntityById);

            }

            for (Map.Entry<Integer, RepairFormEntity> entry : repairFormByIdMap.entrySet()) {
                RepairFormEntity repairFormEntity = entry.getValue();
                repairFormEntity.setAuthor(userByIdMap.get(repairFormEntity.getAuthor().getUserId()));
                if (repairFormEntity.getRepairman() != null) {
                    repairFormEntity.setRepairman(userByIdMap.get(repairFormEntity.getRepairman().getUserId()));
                }
            }
        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return null;
        }
        return repairFormEntityById;
    }

    private UserEntity getAuthorUserEntity(ResultSet resultSet, int author_id) throws SQLException {
        UserEntity authorId;
        String email = resultSet.getString("author_email");
        String firstName = resultSet.getString("author_first_name");
        String lastName = resultSet.getString("author_last_name");
        String password = resultSet.getString("author_password");
        BigDecimal amount = resultSet.getBigDecimal("author_amount");

        authorId = new UserEntityBuilder()
                .setUserId(author_id)
                .setEmail(email)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPassword(password)
                .setAmount(amount)
                .build();
        return authorId;
    }

    private UserEntity getRepairmanUserEntity(ResultSet resultSet, int repairmanId) throws SQLException {
        UserEntity repairmanEntityById;
        String repairmanEmail = resultSet.getString("repairman_email");
        String repairmanFirstName = resultSet.getString("repairman_first_name");
        String repairmanLastName = resultSet.getString("repairman_last_name");
        String repairmanPassword = resultSet.getString("repairman_password");
        BigDecimal repairmanAmount = resultSet.getBigDecimal("repairman_amount");


        repairmanEntityById = new UserEntityBuilder()
                .setUserId(repairmanId)
                .setEmail(repairmanEmail)
                .setFirstName(repairmanFirstName)
                .setLastName(repairmanLastName)
                .setPassword(repairmanPassword)
                .setAmount(repairmanAmount)
                .build();
        return repairmanEntityById;
    }

    private RepairFormEntity getRepairFormEntity(int repairFormId, ResultSet resultSet) throws SQLException {
        RepairFormEntity repairFormEntityById;
        String car = resultSet.getString("car");
        LocalDateTime creationDate = resultSet.getObject("creation_date", LocalDateTime.class);
        String description = resultSet.getString("rf_description");
        String feedback = resultSet.getString("feedback");
        LocalDateTime lastModifiedDate = resultSet.getObject("last_modified_date", LocalDateTime.class);
        BigDecimal price = resultSet.getBigDecimal("price");
        String shortDescription = resultSet.getString("rf_s_description");
        Status status = Status.valueOf(resultSet.getString("rf_status"));

        repairFormEntityById = new RepairFormBuilder()
                .setId(repairFormId)
                .setCar(car)
                .setCreationDate(creationDate)
                .setDescription(description)
                .setFeedback(feedback)
                .setLastModifiedDate(lastModifiedDate)
                .setPrice(price)
                .setShortDescription(shortDescription)
                .setStatus(status)
                .build();
        return repairFormEntityById;
    }

    @Override
    public List<RepairFormEntity> findAll() {
        throw new UnsupportedOperationException("Method doesn't implemented");
    }

    public List<RepairFormDto> findAll(int limit, int offset, String sortField, String sortDir,
                                       Integer repairmanIdFilter, Status statusFilter) {
        if (repairmanIdFilter == null) {
            if (statusFilter == null) {
                log.info("all forms");
                return findAll(limit, offset, sortField, sortDir);
            }
            log.info("by status");
            return findByStatus(limit, offset, sortField, sortDir, statusFilter);
        }

        if (statusFilter == null) {
            log.info("by master");
            return findByRepairman(repairmanIdFilter, limit, offset, sortField, sortDir);
        }

        log.info("by status and by master");
        return findByStatusAndRepairman(limit, offset, sortField, sortDir,
                repairmanIdFilter, statusFilter);
    }

    public List<RepairFormDto> findByStatusAndRepairman(int limit, int offset, String sortField, String sortDir,
                                                        Integer repairmanIdFilter, Status statusFilter) {
        String query = getQueryWithSortFieldAndDir(sortField, sortDir,
                Constants.SELECT_ALL_REPAIR_FORMS_BY_STATUS_AND_REPAIRMAN);

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, repairmanIdFilter);
            preparedStatement.setString(2, statusFilter.name());
            preparedStatement.setInt(3, repairmanIdFilter);
            preparedStatement.setString(4, statusFilter.name());
            preparedStatement.setInt(5, limit);
            preparedStatement.setInt(6, offset);
            return getAllRepairFormsDto(preparedStatement);
        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return null;
        }
    }

    private List<RepairFormDto> findByStatus(int limit, int offset, String sortField, String sortDir, Status statusFilter) {
        String query = getQueryWithSortFieldAndDir(sortField, sortDir, Constants.SELECT_ALL_REPAIR_FORMS_BY_STATUS);
        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, statusFilter.name());
            preparedStatement.setString(2, statusFilter.name());
            preparedStatement.setInt(3, limit);
            preparedStatement.setInt(4, offset);
            return getAllRepairFormsDto(preparedStatement);
        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return null;
        }
    }

    public List<RepairFormDto> findAll(int limit, int offset, String sortField, String sortDir) {
        String query = getQueryWithSortFieldAndDir(sortField, sortDir, Constants.SELECT_ALL_REPAIR_FORMS);
        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            return getAllRepairFormsDto(preparedStatement);

        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return null;
        }
    }

    public List<RepairFormDto> findByRepairman(int userId, int limit, int offset, String sortField, String sortDir) {
        String query = getQueryWithSortFieldAndDir(sortField, sortDir, Constants.SELECT_REPAIRMAN_REPAIR_FORMS);
        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, limit);
            preparedStatement.setInt(4, offset);
            return getAllRepairFormsDto(preparedStatement);

        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return null;
        }
    }

    public List<RepairFormDto> findByUserId(Integer userId, int limit, int offset, String sortField, String sortDir) {
        String query = getQueryWithSortFieldAndDir(sortField, sortDir, Constants.SELECT_USER_REPAIR_FORMS);

        List<RepairFormDto> repairFormDtoList = new ArrayList<>();
        int formsCount = 0;
        BigDecimal amount = null;

        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, limit);
            preparedStatement.setInt(5, offset);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                formsCount = resultSet.getInt("totalForms");
                amount = resultSet.getBigDecimal("amount");
                RepairFormDto dto = getRepairFormDto(resultSet);
                repairFormDtoList.add(dto);
            }
        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return null;
        }
        totalForms = formsCount;
        if (amount != null) {
            userAmount = amount;
        }

        return repairFormDtoList;
    }

    @Override
    public RepairFormEntity update(RepairFormEntity entity) {
        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(Constants.UPDATE_REPAIR_FORM);
            ps.setBigDecimal(1, entity.getPrice());
            ps.setString(2, entity.getStatus().name());
            ps.setInt(3, entity.getRepairman().getUserId());
            ps.setObject(4, LocalDateTime.now());
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getStackTrace());
        }
        return entity;
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Method doesn't implemented");
    }

    public int getTotalForms() {
        return totalForms;
    }

    public BigDecimal getUserAmount() {
        return userAmount;
    }

    private RepairFormDto getRepairFormDto(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String car = resultSet.getString("car");
        LocalDateTime creationDate = resultSet.getObject("creation_date", LocalDateTime.class);
        LocalDateTime lastModifiedDate = resultSet.getObject("last_modified_date", LocalDateTime.class);
        BigDecimal price = resultSet.getBigDecimal("price");
        String shortDescription = resultSet.getString("rf_s_description");
        Status status = Status.valueOf(resultSet.getString("rf_status"));
        int authorId = resultSet.getInt("author_id");
        String authorFirstName = resultSet.getString("author_first_name");
        String authorLastName = resultSet.getString("author_last_name");
        int repairmanId = resultSet.getInt("repairman_id");
        String repairmanFirstName = resultSet.getString("repairman_first_name");
        String repairmanLastName = resultSet.getString("repairman_last_name");


        return new RepairFormDtoBuilder()
                .setId(id)
                .setCar(car)
                .setCreationDate(creationDate)
                .setAuthorId(authorId)
                .setAuthorFirstName(authorFirstName)
                .setAuthorLastName(authorLastName)
                .setRepairmanId(repairmanId)
                .setRepairmanFirstName(repairmanFirstName)
                .setRepairmanLastName(repairmanLastName)
                .setLastModifiedDate(lastModifiedDate)
                .setPrice(price)
                .setShortDescription(shortDescription)
                .setStatus(status)
                .build();
    }

    private String getQueryWithSortFieldAndDir(String sortField, String sortDir, String sql) {
        if (sortDir == null || sortDir.isEmpty()) {
            sortDir = "ASC";
        }
        if (sortField == null || sortField.isEmpty()) {
            sortField = "r.id";
        }

        return sql.replaceAll("ORDER BY \\?", "ORDER BY " + sortField + " " + sortDir);
    }

    public List<RepairFormDto> getAllRepairFormsDto(PreparedStatement ps) {
        List<RepairFormDto> repairFormDtoList = new ArrayList<>();
        int formsCount = 0;

        ResultSet resultSet = null;
        try {
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                formsCount = resultSet.getInt("totalForms");
                RepairFormDto dto = getRepairFormDto(resultSet);
                repairFormDtoList.add(dto);
            }
        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return null;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                    ps.close();
                    ps.getConnection().close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        totalForms = formsCount;

        return repairFormDtoList;

    }

    public void saveFeedback(int id, String feedback) {
        try (Connection con = DbUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(Constants.SAVE_REVIEW);
            ps.setString(1, feedback);
            ps.setObject(2, LocalDateTime.now());
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getStackTrace());
        }

    }

    public boolean writeOffFunds(int id, int authorId, Status status, int repairmanId, BigDecimal price) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = DbUtil.getConnection();
            ps = con.prepareStatement(Constants.GET_AMOUNT);
            ps.setInt(1, authorId);
            resultSet = ps.executeQuery();
            BigDecimal amount = null;
            while (resultSet.next()) {
                amount = resultSet.getBigDecimal("amount");
            }
            if (amount == null || amount.compareTo(price) < 0) {
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

            ps = con.prepareStatement(Constants.UPDATE_AMOUNT);
            ps.setBigDecimal(1, amount.subtract(price));
            ps.setInt(2, authorId);
            ps.executeUpdate();

            ps = con.prepareStatement(Constants.GET_AMOUNT);
            ps.setInt(1, authorId);
            resultSet = ps.executeQuery();
            amount = null;
            while (resultSet.next()) {
                amount = resultSet.getBigDecimal("amount");
            }
            if (amount == null || amount.doubleValue() < 0) {
                con.rollback();
            } else {
                con.commit();
            }
            con.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            log.error(e.getStackTrace());
            return false;
        } finally {
            try {
                if (con != null && ps != null && resultSet != null) {
                    resultSet.close();
                    ps.close();
                    con.close();
                }
            } catch (SQLException e) {
                log.error(e.getStackTrace());
            }
        }
    }
}
