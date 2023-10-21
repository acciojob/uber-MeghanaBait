package com.driver.model;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Table(name = "Admin")

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;
    private String userName;
    private String password;

    public Admin() {
    }

    public Admin(Integer adminId, String userName, String password) {
        this.adminId = adminId;
        this.userName = userName;
        this.password = password;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}