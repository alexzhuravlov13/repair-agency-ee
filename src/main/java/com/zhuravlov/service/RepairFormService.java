package com.zhuravlov.service;

import com.zhuravlov.db.Dao.RepairFormDaoImpl;
import com.zhuravlov.db.Dao.UserDaoImpl;
import com.zhuravlov.model.entity.RepairFormEntity;

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

    public List<RepairFormEntity> findAll(int limit, int offset) {
        return dao.findAll(limit, offset);
    }

    public List<RepairFormEntity> findAll() {
        return dao.findAll();
    }

    public List<RepairFormEntity> findAll(int limit, int offset, String sortField, String sortDir) {
        return dao.findAll(limit, offset, sortField, sortDir);
    }

    public int getFormsCount(){
        return dao.getTotalForms();
    }


}
