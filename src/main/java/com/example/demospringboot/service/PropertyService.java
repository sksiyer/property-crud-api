package com.example.demospringboot.service;

import com.example.demospringboot.data.FileHandler;
import com.example.demospringboot.model.Property;
import com.example.demospringboot.data.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {
    @Autowired
    FileHandler fileHandler;

    public PropertyDTO create(Property property) {
        try {
            return fileHandler.write(property);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create Property", e);
        }
    }

    public List<PropertyDTO> read(){
        return fileHandler.read();
    }

    public PropertyDTO readById(Integer id){
        checkId(id);
        return fileHandler.readById(id);
    }

    public PropertyDTO update(Property property, Integer id) {
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
        List<PropertyDTO> matchingProperties = searchByLondonAreaCode(areacode);
        Double total = Double.valueOf(0.0);

        if(matchingProperties.size() == 0){
            return "No postcodes with area code " + areacode;
        }

        for (PropertyDTO property : matchingProperties) {
            total = total + property.getSizeBySqrFoot();
        }

        return String.valueOf(total/matchingProperties.size());

    }

    private List<PropertyDTO> searchByLondonAreaCode(String areacode) {
        List<PropertyDTO> searchResults = new ArrayList<>();
        List<PropertyDTO> propertyDTOList = fileHandler.read();

        for (PropertyDTO propertyDTO : propertyDTOList) {
            String postcode = propertyDTO.getAddress().getPostcode();

            // Check if the postcode starts with the target area code
            if (postcode.toUpperCase().startsWith(areacode.toUpperCase() + " ")) {
                searchResults.add(propertyDTO);
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
