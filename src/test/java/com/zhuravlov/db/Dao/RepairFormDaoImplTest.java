package com.zhuravlov.db.Dao;

import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.UserEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class RepairFormDaoImplTest {
    private static RepairFormDaoImpl dao;

    private RepairFormEntity getRepairFormEntity() {
        UserDaoImpl userDao = new UserDaoImpl();
        UserEntity user = TestUtil.getUser();
        user = userDao.create(user);
        RepairFormEntity repairForm = TestUtil.getRepairForm();
        repairForm.setAuthor(user);
        return repairForm;
    }

    @BeforeClass
    public static void setup() throws SQLException {
        dao = new RepairFormDaoImpl();
        TestUtil.initH2();

        Connection connection = DbUtil.getConnection();
        Statement statement = connection.createStatement();
        try {
            TestUtil.executeDBScripts("sql\\schema.sql", statement);
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void create() {
        RepairFormEntity repairForm = getRepairFormEntity();
        System.out.println(dao.create(repairForm));
        assertEquals(repairForm, dao.create(repairForm));
    }

    @Test
    public void findById() {
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm = dao.create(repairForm);
        System.out.println(repairForm);
        assertEquals(1, dao.findById(1).getId());

    }

    @Test
    public void findAll() {
    }

    @Test
    public void testFindAll() {
    }

    @Test
    public void findByStatusAndRepairman() {
    }

    @Test
    public void testFindAll1() {
    }

    @Test
    public void findByRepairman() {
    }

    @Test
    public void findByUserId() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getTotalForms() {
    }

    @Test
    public void getUserAmount() {
    }

    @Test
    public void getAllRepairFormsDto() {
    }

    @Test
    public void saveFeedback() {
    }

    @Test
    public void writeOffFunds() {
    }


}