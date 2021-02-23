package com.zhuravlov.service;

import com.zhuravlov.db.Dao.RepairFormDaoImpl;
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.dto.RepairFormDto;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public RepairFormEntity create(String car, String shortDescription, String description, UserEntity user) {
        return dao.create(RepairFormBuilder.getInstance()
                .setCar(car)
                .setDescription(description)
                .setShortDescription(shortDescription)
                .setCreationDate(LocalDateTime.now())
                .setStatus(Status.NEW)
                .setAuthor(user)
                .build());
    }

    public List<RepairFormDto> findAll(int limit, int offset, String sortField, String sortDir,
                                       Integer repairmanIdFilter, Status statusFilter) {
        return dao.findAll(limit, offset, sortField, sortDir, repairmanIdFilter, statusFilter);
    }

    public int getFormsCount() {
        return dao.getTotalForms();
    }

    public BigDecimal getAmount() {
        return dao.getUserAmount();
    }


    public List<RepairFormDto> findByUserId(Integer userId, int limit, int offset, String sortField, String sortDir) {
        return dao.findByUserId(userId, limit, offset, sortField, sortDir);
    }

    public boolean saveReview(int id, String feedback) {
        return dao.saveFeedback(id, feedback);
    }

    public boolean writeOffFunds(int id, int authorId, Status status, int repairmanId, BigDecimal price) {
        return dao.writeOffFunds(id, authorId, status, repairmanId, price);
    }

    public RepairFormEntity findById(int repairFormId) {
        return dao.findById(repairFormId);
    }

    public List<RepairFormDto> findByRepairman(int repairmanId, int limit, int offset, String sortField, String sortDir) {
        return dao.findByRepairman(repairmanId, limit, offset, sortField, sortDir);
    }

    public static RepairFormService getInstance() {
        return new RepairFormService();
    }

    public RepairFormEntity updateRepairForm(RepairFormEntity editedForm) {
        return dao.update(editedForm);
    }
}
