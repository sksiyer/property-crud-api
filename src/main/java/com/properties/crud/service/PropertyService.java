package com.properties.crud.service;

import com.properties.crud.data.FileHandler;
import com.properties.crud.model.Property;
import com.properties.crud.data.PropertyData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {

    /**
     * Add property to file with unique id
     * @param property to saved
     * @return property saved with id
     */
    public PropertyData create(Property property) {
        try {
            return FileHandler.write(property);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create Property", e);
        }
    }

    /**
     * Retrieve all the properties on file in order of insertion
     * @return list of properties with id
     */
    public List<PropertyData> read(){
        return FileHandler.read();
    }

    /**
     * Retrieve a property by id given
     * @param id
     * @return property with its id
     */
    public PropertyData readById(Integer id){
        checkId(id);
        return FileHandler.readById(id);
    }

    /**
     * Update a property with a given id
     * @param property updated property
     * @param id property to update
     * @return updated property with its id
     */
    public PropertyData update(Property property, Integer id) {
        checkId(id);
        try {
            return FileHandler.update(id, property);
        } catch (IOException e) {
            throw new RuntimeException("Unable to update item", e);
        }
    }

    /**
     * Property to delete
     * @param id property to delete
     */
    public void delete(Integer id) {
        try {
            checkId(id);
            FileHandler.delete(id);
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
        List<PropertyData> propertyDataList = FileHandler.read();

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
        if(FileHandler.readById(id) == null){
            throw new RuntimeException("No property found with id: " + id);
        }
    }


}
