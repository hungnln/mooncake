package com.hungnln.mooncake.dtos;

import javax.persistence.*;

@Table(name = "tbl_Users")
@Entity
public class User {
    @Id
    @Column(name = "userID", nullable = false, length = 30)
    private String id;

    @Column(name = "userPassword", nullable = false, length = 30)
    private String userPassword;

    @Column(name = "userPhoneNumber", nullable = false, length = 11)
    private String userPhoneNumber;

    @Column(name = "userAddress", nullable = false, length = 90)
    private String userAddress;

    @ManyToOne(optional = false)
    @JoinColumn(name = "roleId", nullable = false)
    private Role roleId;

    @Column(name = "userName", nullable = false, length = 90)
    private String userName;

    public User() {
    }

    public User(String id, String userPassword, String userPhoneNumber, String userAddress, Role roleId, String userName) {
        this.id = id;
        this.userPassword = userPassword;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.roleId = roleId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}