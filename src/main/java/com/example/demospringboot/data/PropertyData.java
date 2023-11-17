package com.example.demospringboot.data;

import com.example.demospringboot.model.Property;

public class PropertyData extends Property {
    private int id;

    public PropertyData(Property property, Integer id){
        this.id = id;
        this.setAddress(property.getAddress());
        this.setNoOfBedrooms(property.getNoOfBedrooms());
        this.setSizeBySqrFoot(property.getSizeBySqrFoot());
        this.setPurchasePrice(property.getPurchasePrice());
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public void copy(Property property){
        this.setAddress(property.getAddress());
        this.setNoOfBedrooms(property.getNoOfBedrooms());
        this.setSizeBySqrFoot(property.getSizeBySqrFoot());
        this.setPurchasePrice(property.getPurchasePrice());
    }
}
