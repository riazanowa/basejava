package com.urise.webapp.model;

import java.io.Serializable;
import java.util.List;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Period> periods;
    private Link link;

    public Organization(Link link, List<Period> periods) {
        this.link = link;
        this.periods = periods;
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

        Organization that = (Organization) o;

        if (periods != null ? !periods.equals(that.periods) : that.periods != null) return false;
        return link != null ? link.equals(that.link) : that.link == null;
    }

    @Override
    public int hashCode() {
        int result = periods != null ? periods.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        if (!periods.isEmpty()) {
            for (Period period : periods) {
                sb.append(period.getStartDate() + " " + period.getEndDate() + " " + " " + period.getPosition() + " " + period.getDescription());
            }
        }
        return sb.toString();
    }
}
