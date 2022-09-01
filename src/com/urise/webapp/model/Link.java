package com.urise.webapp.model;

import java.io.Serializable;

public class Link implements Serializable {
    private String link;

    public Link(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link1 = (Link) o;

        return link.equals(link1.link);
    }

    @Override
    public int hashCode() {
        return link.hashCode();
    }

    @Override
    public String toString() {
        return link;
    }
}
