package com.example.demospringboot.service;

import com.example.demospringboot.data.FileHandler;
import com.example.demospringboot.model.Property;
import com.example.demospringboot.data.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    private void checkId(Integer id) {
        if(fileHandler.readById(id) == null){
            throw new RuntimeException("No property found with id: " + id);
        }
    }


}
