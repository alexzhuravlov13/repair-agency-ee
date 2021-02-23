package com.zhuravlov.service;

import com.zhuravlov.db.Dao.RepairFormDaoImpl;
import com.zhuravlov.model.builder.RepairFormBuilder;
import com.zhuravlov.model.dto.RepairFormDto;
import com.zhuravlov.model.entity.RepairFormEntity;
import com.zhuravlov.model.entity.Status;
import com.zhuravlov.model.entity.UserEntity;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class RepairFormServiceTest {
    private RepairFormService service;
    private RepairFormDaoImpl mockDao;

    @Before
    public void init() {
        mockDao = mock(RepairFormDaoImpl.class);
        service = new RepairFormService(mockDao);
    }

    @Test
    public void create() {
        RepairFormEntity repairFormEntity = RepairFormBuilder.getInstance().build();
        when(mockDao.create(any(RepairFormEntity.class))).thenReturn(repairFormEntity);
        assertEquals(repairFormEntity, service.create(repairFormEntity));
    }

    @Test
    public void Create2() {
        RepairFormEntity repairFormEntity = RepairFormBuilder.getInstance().build();
        when(mockDao.create(any(RepairFormEntity.class))).thenReturn(repairFormEntity);
        assertEquals(repairFormEntity, service.create("", "", "", new UserEntity()));
    }

    @Test
    public void findAll() {
        List<RepairFormDto> all = Arrays.asList(new RepairFormDto(), new RepairFormDto());
        when(mockDao.findAll(anyInt(), anyInt(), anyString(), anyString(), anyInt(), any(Status.class))).thenReturn(all);
        assertEquals(all, service.findAll(1, 2, "", "", 3, Status.NEW));
    }

    @Test
    public void getFormsCount() {
        int totalForms = 10;
        when(mockDao.getTotalForms()).thenReturn(totalForms);
        assertEquals(totalForms, service.getFormsCount());
    }

    @Test
    public void getAmount() {
        BigDecimal amount = new BigDecimal(10);
        when(mockDao.getUserAmount()).thenReturn(amount);
        assertEquals(amount, service.getAmount());
    }

    @Test
    public void findByUserId() {
        RepairFormEntity repairFormEntity = RepairFormBuilder.getInstance().build();
        when(mockDao.findById(anyInt())).thenReturn(repairFormEntity);
        assertEquals(repairFormEntity, service.findById(1));
    }

    @Test
    public void saveReview() {
        when(mockDao.saveFeedback(anyInt(), anyString())).thenReturn(true);
        assertTrue(service.saveReview(1, ""));
    }

    @Test
    public void updateRepairForm() {
        RepairFormEntity repairFormEntity = RepairFormBuilder.getInstance().build();
        when(mockDao.update(any(RepairFormEntity.class))).thenReturn(repairFormEntity);
        assertEquals(repairFormEntity, service.updateRepairForm(repairFormEntity));

    }

    @Test
    public void writeOffFunds() {
        RepairFormEntity repairFormEntity = RepairFormBuilder.getInstance().build();
        when(mockDao.writeOffFunds(anyInt(), anyInt(), any(Status.class), anyInt(), any(BigDecimal.class))).thenReturn(true);
        assertTrue(service.writeOffFunds(1, 2, Status.NEW, 3, new BigDecimal(0)));
    }

    @Test
    public void findById() {
        RepairFormEntity repairFormEntity = RepairFormBuilder.getInstance().build();
        when(mockDao.findById(anyInt())).thenReturn(repairFormEntity);
        assertEquals(repairFormEntity, service.findById(1));
    }

    @Test
    public void findByRepairman() {
        List<RepairFormDto> all = Arrays.asList(new RepairFormDto(), new RepairFormDto());
        when(mockDao.findByRepairman(anyInt(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(all);
        assertEquals(all, service.findByRepairman(1, 2, 3, "", ""));
    }

    @Test
    public void getInstance() {
        assertNotNull(RepairFormService.getInstance());
    }

}