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

    public List<RepairFormEntity> findAll(int start, int count) {
        return dao.findAll(start, count);
    }
}
