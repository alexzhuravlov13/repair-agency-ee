package com.zhuravlov.db.Dao;

import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import com.zhuravlov.db.Constants;
import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.entity.RepairFormEntity;

import java.sql.*;
import java.util.List;

public class RepairFormDaoImpl implements Dao<RepairFormEntity> {

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
            ps.execute();

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

        return null;
    }

    public List<RepairFormEntity> findAll(int start, int count) {

        return null;
    }

    @Override
    public RepairFormEntity update(RepairFormEntity entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }


}
