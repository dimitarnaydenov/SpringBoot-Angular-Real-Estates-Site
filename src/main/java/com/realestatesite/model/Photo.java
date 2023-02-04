package com.realestatesite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String url;

    @ManyToOne()
    @JsonIgnore
    private Property property;

    @Column(length = 1000)
    private byte[] data;

    public Photo() {
    }

    public Photo(String url, Property property, byte[] data) {
        this.url = url;
        this.property = property;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
