package com.hungnln.mooncake.dtos;

import javax.persistence.*;

import java.time.LocalDate;

@Table(name = "tbl_Cakes")
@Entity
public class Cake {
    @Id
    @Column(name = "cakeID", nullable = false, length = 10)
    private String cakeID;

    @Column(name = "cakeName", nullable = false, length = 30)
    private String cakeName;

    @Column(name = "cakeDescription", nullable = false, length = 90)
    private String cakeDescription;

    @Column(name = "cakePrice", nullable = false)
    private Integer cakePrice;

    @Column(name = "cakeQuantity", nullable = false)
    private Integer cakeQuantity;

    @Column(name = "createDate", nullable = false)
    private LocalDate createDate;

    @Column(name = "expirationDate", nullable = false)
    private LocalDate expirationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryID", nullable = false)
    private Category category;

    @Column(name = "cakeStatus", nullable = false)
    private Boolean cakeStatus = false;

    @Column(name = "cakeImage", length = 100)
    private String cakeImage;

    public Cake() {
    }

    public Cake(String cakeID, String cakeName, String cakeDescription, Integer cakePrice, Integer cakeQuantity, LocalDate createDate, LocalDate expirationDate, Category category, Boolean cakeStatus, String cakeImage) {
        this.cakeID = cakeID;
        this.cakeName = cakeName;
        this.cakeDescription = cakeDescription;
        this.cakePrice = cakePrice;
        this.cakeQuantity = cakeQuantity;
        this.createDate = createDate;
        this.expirationDate = expirationDate;
        this.category = category;
        this.cakeStatus = cakeStatus;
        this.cakeImage = cakeImage;
    }

    public String getCakeImage() {
        return cakeImage;
    }

    public void setCakeImage(String cakeImage) {
        this.cakeImage = cakeImage;
    }

    public Boolean getCakeStatus() {
        return cakeStatus;
    }

    public void setCakeStatus(Boolean cakeStatus) {
        this.cakeStatus = cakeStatus;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getCakeQuantity() {
        return cakeQuantity;
    }

    public void setCakeQuantity(Integer cakeQuantity) {
        this.cakeQuantity = cakeQuantity;
    }

    public Integer getCakePrice() {
        return cakePrice;
    }

    public void setCakePrice(Integer cakePrice) {
        this.cakePrice = cakePrice;
    }

    public String getCakeDescription() {
        return cakeDescription;
    }

    public void setCakeDescription(String cakeDescription) {
        this.cakeDescription = cakeDescription;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public String getCakeID() {
        return cakeID;
    }

    public void setCakeID(String cakeID) {
        this.cakeID = cakeID;
    }
}