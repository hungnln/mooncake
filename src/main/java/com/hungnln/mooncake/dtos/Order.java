package com.hungnln.mooncake.dtos;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "tbl_Orders")
@Entity
public class Order {
    @Id
    @Column(name = "orderID", nullable = false, length = 10)
    private String id;

    @Column(name = "orderTotal", nullable = false)
    private Double orderTotal;

    @Column(name = "orderDate", nullable = false)
    private LocalDate orderDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userID", nullable = false)
    private User userID;

    @Column(name = "orderStatus", nullable = false)
    private Integer orderStatus;

    @Column(name = "orderPayment", nullable = false, length = 10)
    private String orderPayment;

    @Column(name = "phoneNumber", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "fullName", nullable = false, length = 30)
    private String fullName;

    @Column(name = "address", nullable = false, length = 90)
    private String address;


    public Order(String id, Double orderTotal, LocalDate orderDate, User userID, Integer orderStatus, String orderPayment, String phoneNumber, String fullName, String address) {
        this.id = id;
        this.orderTotal = orderTotal;
        this.orderDate = orderDate;
        this.userID = userID;
        this.orderStatus = orderStatus;
        this.orderPayment = orderPayment;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}