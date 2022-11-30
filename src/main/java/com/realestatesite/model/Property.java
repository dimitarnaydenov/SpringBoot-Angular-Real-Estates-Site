package com.realestatesite.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String type;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String address;

    private String description;

    @NotNull
    private int area;

    @NotNull
    private String phoneNumber;

    @NotNull
    private int beds;

    @NotNull
    private int baths;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Photo> photos = new ArrayList<>();

    @ManyToOne()
    private CustomUser customUser;

    public Property(String type, BigDecimal price, String address, String description, int area,
                    String phoneNumber, int beds, int baths, Collection<Photo> photos, CustomUser customUser) {
        this.type = type;
        this.price = price;
        this.address = address;
        this.description = description;
        this.area = area;
        this.phoneNumber = phoneNumber;
        this.beds = beds;
        this.baths = baths;
        this.photos = photos;
        this.customUser = customUser;
    }

    public Property() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Collection<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<Photo> photos) {
        this.photos = photos;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
    }
}
