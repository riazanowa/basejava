package com.urise.webapp.model;

import java.io.Serializable;
import java.util.List;

public class ExperienceSection extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations;

    public ExperienceSection(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Organization> getExperienceStages() {
        return organizations;
    }

    public void setExperienceStages(List<Organization> organizations) {
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (Organization stage : organizations) {
            result.append(stage + "\n");
        }
        return result.toString();
    }
}
