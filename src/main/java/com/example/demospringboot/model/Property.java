package com.example.demospringboot.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Property {
    private Address address;
    private int noOfBedrooms;
    private double sizeBySqrFoot;
//    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;

    public Property() {
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getNoOfBedrooms() {
        return noOfBedrooms;
    }

    public void setNoOfBedrooms(int noOfBedrooms) {
        this.noOfBedrooms = noOfBedrooms;
    }

    public double getSizeBySqrFoot() {
        return sizeBySqrFoot;
    }

    public void setSizeBySqrFoot(double sizeBySqrFoot) {
        this.sizeBySqrFoot = sizeBySqrFoot;
    }

//    public LocalDate getPurchaseDate() {
//        return purchaseDate;
//    }
//
//    public void setPurchaseDate(LocalDate purchaseDate) {
//        this.purchaseDate = purchaseDate;
//    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
