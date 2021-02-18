package com.zhuravlov.model.builder;


import com.zhuravlov.model.dto.RepairFormDto;
import com.zhuravlov.model.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepairFormDtoBuilder {
    private RepairFormDto repairFormDto;

    public RepairFormDtoBuilder() {
        this.repairFormDto = new RepairFormDto();
    }

    public RepairFormDtoBuilder setId(int id) {
        this.repairFormDto.setId(id);
        return this;
    }

    public RepairFormDtoBuilder setCreationDate(LocalDateTime creationDate) {
        this.repairFormDto.setCreationDate(creationDate);
        return this;
    }

    public RepairFormDtoBuilder setAuthorId(int authorId) {
        this.repairFormDto.setAuthorId(authorId);
        return this;
    }

    public RepairFormDtoBuilder setAuthorFirstName(String authorFirstName) {
        this.repairFormDto.setAuthorFirstName(authorFirstName);
        return this;
    }

    public RepairFormDtoBuilder setAuthorLastName(String authorLastName) {
        this.repairFormDto.setAuthorLastName(authorLastName);
        return this;
    }

    public RepairFormDtoBuilder setCar(String car) {
        this.repairFormDto.setCar(car);
        return this;
    }

    public RepairFormDtoBuilder setShortDescription(String shortDescription) {
        this.repairFormDto.setShortDescription(shortDescription);
        return this;
    }

    public RepairFormDtoBuilder setRepairmanId(int repairmanId) {
        this.repairFormDto.setRepairmanId(repairmanId);
        return this;
    }

    public RepairFormDtoBuilder setRepairmanFirstName(String repairmanFirstName) {
        this.repairFormDto.setRepairmanFirstName(repairmanFirstName);
        return this;
    }

    public RepairFormDtoBuilder setRepairmanLastName(String repairmanLastName) {
        this.repairFormDto.setRepairmanLastName(repairmanLastName);
        return this;
    }

    public RepairFormDtoBuilder setStatus(Status status) {
        this.repairFormDto.setStatus(status);
        return this;
    }

    public RepairFormDtoBuilder setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.repairFormDto.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public RepairFormDtoBuilder setPrice(BigDecimal price) {
        this.repairFormDto.setPrice(price);
        return this;
    }

    public RepairFormDto build() {
        return repairFormDto;
    }

}
