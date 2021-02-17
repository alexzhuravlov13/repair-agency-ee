package com.zhuravlov.service;

import com.zhuravlov.db.Dao.RepairFormDaoImpl;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;

import java.math.BigDecimal;
import java.util.List;

public class RepairFormService {
    private RepairFormDaoImpl dao;

    public RepairFormService() {
        dao = new RepairFormDaoImpl();
    }

    public RepairFormService(RepairFormDaoImpl dao) {
        this.dao = dao;
    }

    public RepairFormEntity create(RepairFormEntity repairForm) {
        return dao.create(repairForm);
    }

    public List<RepairFormEntity> findAll() {
        return dao.findAll();
    }

    public List<RepairFormEntity> findAll(int limit, int offset, String sortField, String sortDir) {
        return dao.findAll(limit, offset, sortField, sortDir);
    }

    public int getFormsCount() {
        return dao.getTotalForms();
    }


    public List<RepairFormEntity> findByUserId(Integer userId, int limit, int offset, String sortField, String sortDir) {
        return dao.findByUserId(userId, limit, offset, sortField, sortDir);
    }

    public void saveReview(int id, String feedback) {
        dao.saveFeedback(id, feedback);
    }

    public void updateRepairForm(RepairFormEntity editedForm) {
        dao.update(editedForm);
    }

    public boolean writeOffFunds(int id, int authorId, Status status, int repairmanId, BigDecimal price) {
        return dao.writeOffFunds(id, authorId, status, repairmanId, price);
    }

    public RepairFormEntity findById(int repairFormId) {
        return dao.findById(repairFormId);
    }
}
