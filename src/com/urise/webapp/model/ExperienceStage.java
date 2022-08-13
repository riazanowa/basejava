package com.urise.webapp.model;

import java.time.LocalDate;

public class ExperienceStage {
    private LocalDate startDate;
    private LocalDate endDate;
    private String place;
    private String position;
    private String description;

    public ExperienceStage(LocalDate startDate, LocalDate endDate, String place, String position, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceStage that = (ExperienceStage) o;

        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!place.equals(that.place)) return false;
        if (!position.equals(that.position)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + place.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return startDate + " " + endDate + " " + place + " " + position + " " + description;
    }
}
