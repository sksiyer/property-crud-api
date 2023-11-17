package com.example.demospringboot.service;

import com.example.demospringboot.data.FileHandler;
import com.example.demospringboot.model.Property;
import com.example.demospringboot.data.PropertyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {
    @Autowired
    FileHandler fileHandler;

    public PropertyData create(Property property) {
        try {
            return fileHandler.write(property);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create Property", e);
        }
    }

    public List<PropertyData> read(){
        return fileHandler.read();
    }

    public PropertyData readById(Integer id){
        checkId(id);
        return fileHandler.readById(id);
    }

    public PropertyData update(Property property, Integer id) {
        checkId(id);
        try {
            return fileHandler.update(id, property);
        } catch (IOException e) {
            throw new RuntimeException("Unable to update item", e);
        }
    }

    public void delete(Integer id) {
        try {
            checkId(id);
            fileHandler.delete(id);
        } catch (IOException e){
            throw new RuntimeException("Unable to delete item", e);
        }
    }

    public String getAverageSqrFootPrice(String areacode) {
        List<PropertyData> matchingProperties = searchByLondonAreaCode(areacode);
        Double total = Double.valueOf(0.0);

        if(matchingProperties.size() == 0){
            return "No postcodes with area code " + areacode;
        }

        for (PropertyData property : matchingProperties) {
            total = total + property.getSizeBySqrFoot();
        }

        return String.valueOf(total/matchingProperties.size());

    }

    /**
     * As the array stored is in insertion order
     * A linear search is best for
     * @param areacode - the first part of the postcode, i.e. SE1 8AA, this is SE8 part
     * @return the items in list that match areacode
     */
    private List<PropertyData> searchByLondonAreaCode(String areacode) {
        List<PropertyData> searchResults = new ArrayList<>();

        //get all the properties we have
        List<PropertyData> propertyDataList = fileHandler.read();

        for (PropertyData propertyData : propertyDataList) {
            String postcode = propertyData.getAddress().getPostcode();

            // Check if the postcode starts with the target area code
            if (postcode.toUpperCase().startsWith(areacode.toUpperCase() + " ")) {
                searchResults.add(propertyData);
            }
        }

        return searchResults;
    }

    private void checkId(Integer id) {
        if(fileHandler.readById(id) == null){
            throw new RuntimeException("No property found with id: " + id);
        }
    }


}
