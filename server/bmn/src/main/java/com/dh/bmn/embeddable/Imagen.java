package com.dh.bmn.embeddable;

import jakarta.persistence.*;

@Embeddable
public class Imagen {

    @Column (length = 500)
    private String url;

    public Imagen(String url) {
        this.url = url;
    }

    public Imagen() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
