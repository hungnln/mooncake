package com.hungnln.mooncake.dtos;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tbl_Orderdetails")
@Entity
public class OrderDetail implements Serializable {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "orderID", nullable = false)
    private Order orderID;
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "cakeID", nullable = false)
    private Cake cakeID;

    @Column(name = "cakeOrderQuantity", nullable = false)
    private Integer cakeOrderQuantity;


    public OrderDetail(Order orderID, Cake cakeID, Integer cakeOrderQuantity) {
        this.orderID = orderID;
        this.cakeID = cakeID;
        this.cakeOrderQuantity = cakeOrderQuantity;
    }

    public Integer getCakeOrderQuantity() {
        return cakeOrderQuantity;
    }

    public void setCakeOrderQuantity(Integer cakeOrderQuantity) {
        this.cakeOrderQuantity = cakeOrderQuantity;
    }

    public Cake getCakeID() {
        return cakeID;
    }

    public void setCakeID(Cake cakeID) {
        this.cakeID = cakeID;
    }

    public Order getOrderID() {
        return orderID;
    }

    public void setOrderID(Order orderID) {
        this.orderID = orderID;
    }
}