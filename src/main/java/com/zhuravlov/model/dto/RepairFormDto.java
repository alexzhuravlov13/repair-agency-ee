package com.zhuravlov.model.dto;

import com.zhuravlov.model.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class RepairFormDto {
    private int id;

    private LocalDateTime creationDate;

    private int authorId;

    private String authorFirstName;

    private String authorLastName;

    private String car;

    private String shortDescription;

    private int repairmanId;

    private String repairmanFirstName;

    private String repairmanLastName;

    private Status status;

    private LocalDateTime lastModifiedDate;

    private BigDecimal price = BigDecimal.valueOf(0);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getRepairmanId() {
        return repairmanId;
    }

    public void setRepairmanId(int repairmanId) {
        this.repairmanId = repairmanId;
    }

    public String getRepairmanFirstName() {
        return repairmanFirstName;
    }

    public void setRepairmanFirstName(String repairmanFirstName) {
        this.repairmanFirstName = repairmanFirstName;
    }

    public String getRepairmanLastName() {
        return repairmanLastName;
    }

    public void setRepairmanLastName(String repairmanLastName) {
        this.repairmanLastName = repairmanLastName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairFormDto that = (RepairFormDto) o;
        return id == that.id &&
                authorId == that.authorId &&
                repairmanId == that.repairmanId &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(authorFirstName, that.authorFirstName) &&
                Objects.equals(authorLastName, that.authorLastName) &&
                Objects.equals(car, that.car) &&
                Objects.equals(shortDescription, that.shortDescription) &&
                Objects.equals(repairmanFirstName, that.repairmanFirstName) &&
                Objects.equals(repairmanLastName, that.repairmanLastName) &&
                status == that.status &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, authorId, authorFirstName, authorLastName, car, shortDescription, repairmanId, repairmanFirstName, repairmanLastName, status, lastModifiedDate, price);
    }

    @Override
    public String toString() {
        return "RepairFormDto{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", authorId=" + authorId +
                ", authorFirstName='" + authorFirstName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                ", car='" + car + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", repairmanId=" + repairmanId +
                ", repairmanFirstName='" + repairmanFirstName + '\'' +
                ", repairmanLastName='" + repairmanLastName + '\'' +
                ", status=" + status +
                ", lastModifiedDate=" + lastModifiedDate +
                ", price=" + price +
                '}';
    }
}
