package com.driver.model;

import io.swagger.models.auth.In;

import javax.persistence.*;

@Entity
@Table(name="cab")

public class Cab{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cabId;

    private Integer perKmRate;
    private Boolean isAvailable;

    @OneToOne(mappedBy = "cab",cascade = CascadeType.ALL)
    private Driver driver;

    public Cab() {
    }

    public Cab(Integer cabId, Integer perKmRate, Boolean isAvailable, Driver driver) {
        this.cabId = cabId;
        this.perKmRate = perKmRate;
        this.isAvailable = isAvailable;
        this.driver = driver;
    }

    public Integer getCabId() {
        return cabId;
    }

    public void setCabId(Integer cabId) {
        this.cabId = cabId;
    }

    public Integer getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(Integer perKmRate) {
        this.perKmRate = perKmRate;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}