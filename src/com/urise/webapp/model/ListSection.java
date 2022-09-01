package com.urise.webapp.model;

import java.io.Serializable;
import java.util.List;

public class ListSection extends AbstractSection implements Serializable {
    private List<String> sectionItems;

    public ListSection(List<String> sectionItems) {
        this.sectionItems = sectionItems;
    }

    public List<String> getSectionItems() {
        return sectionItems;
    }

    public void setSectionItems(List<String> sectionItems) {
        this.sectionItems = sectionItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return sectionItems.equals(that.sectionItems);
    }

    @Override
    public int hashCode() {
        return sectionItems.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (String item : sectionItems) {
            result.append(item + "\n");
        }

        return result.toString();
    }
}
