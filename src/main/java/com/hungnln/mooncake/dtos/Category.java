package com.hungnln.mooncake.dtos;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tbl_Categories")
@Entity
public class Category {
    @Id
    @Column(name = "categoryID", nullable = false, length = 10)
    private String categoryID;

    @Column(name = "categoryName", nullable = false, length = 50)
    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}