/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.dto;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author asha
 */
public class Booking {
    
    private int id;
    private BigDecimal fare;
    private String status;
    private String customerFeedback;
    private String driverFeedback;
    private float distance;
    private int vehicleId;
    private int customerId;
    private int branchId;
    private Date starttime;
    private Date endtime;
    private Date booktime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerFeedback() {
        return customerFeedback;
    }

    public void setCustomerFeedback(String customerFeedback) {
        this.customerFeedback = customerFeedback;
    }

    public String getDriverFeedback() {
        return driverFeedback;
    }

    public void setDriverFeedback(String driverFeedback) {
        this.driverFeedback = driverFeedback;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getBooktime() {
        return booktime;
    }

    public void setBooktime(Date booktime) {
        this.booktime = booktime;
    }
}
