package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Period implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate startDate;
    private LocalDate endDate;
    private String position;
    private String description;

    public Period(LocalDate startDate, LocalDate endDate, String position, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
