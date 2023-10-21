package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="driver")
public class Driver{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer driverId;

    private String mobileNo;
    private  String password;

    @OneToOne
    @JoinColumn
    private Cab cab;

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public void setTripBookings(List<TripBooking> tripBookings) {
        this.tripBookings = tripBookings;
    }

    public Cab getCab() {
        return cab;
    }

    public List<TripBooking> getTripBookings() {
        return tripBookings;
    }

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<TripBooking> tripBookings = new ArrayList<>();

    public Driver() {
    }

    public Driver(Integer driverId, String mobileNo, String password) {
        this.driverId = driverId;
        this.mobileNo = mobileNo;
        this.password = password;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobileNo;
    }

    public void setMobile(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}