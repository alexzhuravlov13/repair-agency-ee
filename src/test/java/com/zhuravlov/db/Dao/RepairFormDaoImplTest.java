package com.zhuravlov.db.Dao;

import com.zhuravlov.db.DbUtil;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RepairFormDaoImplTest {
    private static RepairFormDaoImpl dao;

    private RepairFormEntity getRepairFormEntity() {
        UserDaoImpl userDao = new UserDaoImpl();
        UserEntity user;
        user = userDao.findById(1);
        if (user == null) {
            user = TestUtil.getUser();
            user = userDao.create(user);
        }
        user.setRoles(new HashSet<>(Arrays.asList(Role.USER, Role.REPAIRMAN)));
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
        assertEquals(repairForm, dao.create(repairForm));
    }

    @Test
    public void findById() {
        RepairFormEntity repairForm = getRepairFormEntity();
        dao.create(repairForm);
        assertEquals(1, dao.findById(1).getId());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void findAll() {
        dao.findAll();
    }

    @Test
    public void testFindAll() {
        int limit = 1;
        int offset = 0;
        String sortField = null;
        String sortDir = null;
        int repairmanId = 1;
        Status status = Status.NEW;
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm.setRepairman(repairForm.getAuthor());
        dao.create(repairForm);
        //without filter
        assertEquals(1, dao.findAll(limit, offset, sortField, sortDir).size());
        //by master
        assertEquals(1, dao.findAll(limit, offset, sortField, sortDir, repairmanId, null).size());
        //by status
        assertEquals(1, dao.findAll(limit, offset, sortField, sortDir, null, status).size());
        //by master and status
        assertEquals(1, dao.findAll(limit, offset, sortField, sortDir, repairmanId, status).size());
        //with sort without filter
        assertEquals(1, dao.findAll(limit, offset, "r.price", "asc").size());

    }

    @Test
    public void findByRepairman() {
        int limit = 1;
        int offset = 0;
        String sortField = null;
        String sortDir = null;
        int repairmanId = 1;
        Status status = Status.NEW;
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm.setRepairman(repairForm.getAuthor());
        dao.create(repairForm);
        //by master
        assertEquals(1, dao.findByRepairman(repairmanId, limit, offset, sortField, sortDir).size());
    }

    @Test
    public void findByUserId() {
        int limit = 1;
        int offset = 0;
        String sortField = null;
        String sortDir = null;
        int userId = 1;
        Status status = Status.NEW;
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm.setRepairman(repairForm.getAuthor());
        dao.create(repairForm);
        //by user
        assertEquals(1, dao.findByUserId(userId, limit, offset, sortField, sortDir).size());
    }

    @Test
    public void update() {
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm.setRepairman(repairForm.getAuthor());
        repairForm = dao.create(repairForm);
        assertEquals(repairForm, dao.update(repairForm));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void delete() {
        dao.delete(1);
    }


    @Test
    public void getTotalForms() {
        int limit = 1;
        int offset = 0;
        String sortField = null;
        String sortDir = null;
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm.setRepairman(repairForm.getAuthor());
        dao.create(repairForm);
        //without filter
        dao.findAll(limit, offset, sortField, sortDir);
        assertTrue( dao.getTotalForms()>0);
    }

    @Test
    public void getUserAmount() {
        assertEquals(BigDecimal.valueOf(0), dao.getUserAmount());
    }


    @Test
    public void saveFeedback() {
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm.setRepairman(repairForm.getAuthor());
        dao.create(repairForm);
        String superStr = "Super";
        dao.saveFeedback(1, superStr);
        assertEquals(superStr, dao.findById(1).getFeedback());
    }

    @Test
    public void writeOffFunds() {
        RepairFormEntity repairForm = getRepairFormEntity();
        repairForm.setRepairman(repairForm.getAuthor());
        dao.create(repairForm);
        assertTrue(dao.writeOffFunds(1, 1, Status.PAID, 1, BigDecimal.valueOf(0)));
    }


}