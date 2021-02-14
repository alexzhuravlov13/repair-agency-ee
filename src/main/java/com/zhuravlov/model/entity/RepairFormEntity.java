package com.zhuravlov.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;


public class RepairFormEntity {

    private int id;

    private LocalDateTime creationDate;

    private UserEntity author;

    private String car;

    private String shortDescription;

    private String description;

    private String feedback;

    private UserEntity repairman;

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

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public UserEntity getRepairman() {
        return repairman;
    }

    public void setRepairman(UserEntity repairman) {
        this.repairman = repairman;
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
    public String toString() {
        return "RepairFormEntity{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", author=" + author +
                ", car='" + car + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", feedback='" + feedback + '\'' +
                ", repairman=" + repairman +
                ", status=" + status +
                ", lastModifiedDate=" + lastModifiedDate +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepairFormEntity that = (RepairFormEntity) o;
        return id == that.id &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(author, that.author) &&
                Objects.equals(car, that.car) &&
                Objects.equals(shortDescription, that.shortDescription) &&
                Objects.equals(description, that.description) &&
                Objects.equals(feedback, that.feedback) &&
                Objects.equals(repairman, that.repairman) &&
                status == that.status &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate, author, car, shortDescription, description, feedback, repairman, status, lastModifiedDate, price);
    }
}
