package com.hungnln.mooncake.dtos;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "tbl_UpdateLogs")
@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logID", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cakeID", nullable = false)
    private Cake cakeID;

    @Column(name = "saveDate")
    private LocalDate saveDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userID", nullable = false)
    private User userID;

    public Log(Cake cakeID, LocalDate saveDate, User userID) {
        this.cakeID = cakeID;
        this.saveDate = saveDate;
        this.userID = userID;
    }

    public User getUserID() {
        return userID;
    }

    public void setUserID(User userID) {
        this.userID = userID;
    }

    public LocalDate getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(LocalDate saveDate) {
        this.saveDate = saveDate;
    }

    public Cake getCakeID() {
        return cakeID;
    }

    public void setCakeID(Cake cakeID) {
        this.cakeID = cakeID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}