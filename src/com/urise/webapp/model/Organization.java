package com.urise.webapp.model;

import java.io.Serializable;
import java.util.List;

public class Organization implements Serializable {
    private String place;
    private List<Period> periods;
    private Link link;

    public Organization(String place, Link link, List<Period> periods) {
        this.place = place;
        this.link = link;
        this.periods = periods;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization stage = (Organization) o;

        if (place != null ? !place.equals(stage.place) : stage.place != null) return false;
        if (periods != null ? !periods.equals(stage.periods) : stage.periods != null) return false;
        return link != null ? link.equals(stage.link) : stage.link == null;
    }

    @Override
    public int hashCode() {
        int result = place != null ? place.hashCode() : 0;
        result = 31 * result + (periods != null ? periods.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        if (!periods.isEmpty()) {
            for (Period period : periods) {
                sb.append(period.getStartDate() + " " + period.getEndDate() + " " + place + " " + period.getPosition() + " " + period.getDescription());
            }
        }
        return sb.toString();
    }
}
