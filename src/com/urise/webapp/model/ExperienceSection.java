package com.urise.webapp.model;

import java.util.List;

public class ExperienceSection extends AbstractSection {
    private List<ExperienceStage> experienceStages;

    public ExperienceSection(List<ExperienceStage> backgroundStages) {
        this.experienceStages = backgroundStages;
    }

    public List<ExperienceStage> getExperienceStages() {
        return experienceStages;
    }

    public void setExperienceStages(List<ExperienceStage> experienceStages) {
        this.experienceStages = experienceStages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceSection that = (ExperienceSection) o;

        return experienceStages.equals(that.experienceStages);
    }

    @Override
    public int hashCode() {
        return experienceStages.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (ExperienceStage stage : experienceStages ) {
            result.append(stage + "\n");
        }
        return result.toString();
    }
}
