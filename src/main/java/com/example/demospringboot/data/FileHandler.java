package com.example.demospringboot.data;

import com.example.demospringboot.model.Property;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileHandler {
    public static String FILE_PATH = "src/main/resources/properties.json";

    public PropertyDTO write(Property property) throws IOException {
        Integer nextId = findNextId();

        PropertyDTO dto = new PropertyDTO(property, findNextId());

        if (nextId == 1) {
            saveFile(List.of(dto));
            return dto;
        }

        List<PropertyDTO> data = read();
        data.add(dto);

        saveFile(data);

        return dto;
    }

    public List<PropertyDTO> read() {
        List<PropertyDTO> list = new ArrayList<>();

        try {
            Type type = new TypeToken<List<PropertyDTO>>() {}.getType();
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(FILE_PATH));
            list = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            // no file found return empty list
        }

        if(list == null){
            return new ArrayList<>();
        }

        return list;
    }

    public PropertyDTO readById(Integer id) {
        List<PropertyDTO> list = read().stream()
                .filter(propertyDTO -> propertyDTO.getId() == id)
                .toList();

        if(list == null || list.size() == 0){
            return null;
        }

        return list.get(0);
    }

    public PropertyDTO update(Integer id, Property property) throws IOException {
        // first get the entire list in properties.json file to update later
        List<PropertyDTO> list = read();

        // Get the property to update by id
        PropertyDTO currentProperty = readById(id);

        // now we have the property, lets set the new values on all the fields
        // from the parameter property given by the user in the api
        currentProperty.copy(property);

        // lets update the current list we have with the new updated property
        // we do this by going through each item on the list
        // and if the id's match, we replace the property with the
        // current property we created above
        List<PropertyDTO> listToSave = list.stream().map(propertyDTO -> {
            if (propertyDTO.getId() == id) {
                return currentProperty;
            }
            return propertyDTO;
        }).toList();

        // write the new list into the file
        try (Writer writer = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(listToSave, writer);
        }


        //return the updated property to the user
        return currentProperty;
    }

    public void delete(Integer id) throws IOException {
        List<PropertyDTO> list = read();
        List<PropertyDTO> listToSave = list.stream()
                .filter(propertyDTO -> propertyDTO.getId() != id)
                .toList();

        saveFile(listToSave);
    }

    public Integer findNextId() {
        List<PropertyDTO> data = read();

        if(data.size() == 0){
            return 0;
        }

        Integer lastIndex = data.size() - 1;

        return data.get(lastIndex).getId() + 1;
    }

    private void saveFile(List<PropertyDTO> listToSave) throws IOException {
        // write the new list into the file
        try (Writer writer = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToSave, writer);
        }
    }

}
