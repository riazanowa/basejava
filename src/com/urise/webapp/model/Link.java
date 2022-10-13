package com.urise.webapp.model;

import java.io.Serializable;

public class Link implements Serializable {
    private static final long serialVersionUID = 1L;
    private String siteName;
    private String url;

    public Link(String siteName, String url) {
        this.siteName = siteName;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link1 = (Link) o;

        return url.equals(link1.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return url;
    }
}
