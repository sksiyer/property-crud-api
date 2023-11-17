package com.properties.crud.data;

import com.properties.crud.model.Property;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public static String FILE_PATH = "src/main/resources/properties.json";

    public static PropertyData write(Property property) throws IOException {
        Integer nextId = findNextId();

        PropertyData data = new PropertyData(property, findNextId());

        if (nextId == 1) {
            saveFile(List.of(data));
            return data;
        }

        List<PropertyData> list = read();
        list.add(data);

        saveFile(list);

        return data;
    }

    public static List<PropertyData> read() {
        List<PropertyData> list = new ArrayList<>();

        try {
            Type type = new TypeToken<List<PropertyData>>() {}.getType();
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

    public static PropertyData readById(Integer id) {
        List<PropertyData> list = read().stream()
                .filter(propertyDATA -> propertyDATA.getId() == id)
                .toList();

        if(list.size() == 0){
            return null;
        }

        return list.get(0);
    }

    public static PropertyData update(Integer id, Property property) throws IOException {
        // first get the entire list in properties.json file to update later
        List<PropertyData> list = read();

        // Get the property to update by id
        PropertyData currentProperty = readById(id);

        // now we have the property, lets set the new values on all the fields
        // from the parameter property given by the user in the api
        currentProperty.copy(property);

        // lets update the current list we have with the new updated property
        // we do this by going through each item on the list
        // and if the id's match, we replace the property with the
        // current property we created above
        List<PropertyData> listToSave = list.stream().map(propertyDATA -> {
            if (propertyDATA.getId() == id) {
                return currentProperty;
            }
            return propertyDATA;
        }).toList();

        // write the new list into the file
        try (Writer writer = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(listToSave, writer);
        }


        //return the updated property to the user
        return currentProperty;
    }

    public static void delete(Integer id) throws IOException {
        List<PropertyData> list = read();
        List<PropertyData> listToSave = list.stream()
                .filter(propertyDATA -> propertyDATA.getId() != id)
                .toList();

        saveFile(listToSave);
    }

    public static Integer findNextId() {
        List<PropertyData> data = read();

        if(data.size() == 0){
            return 0;
        }

        Integer lastIndex = data.size() - 1;

        return data.get(lastIndex).getId() + 1;
    }

    private static void saveFile(List<PropertyData> listToSave) throws IOException {
        // write the new list into the file
        try (Writer writer = new FileWriter(FILE_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToSave, writer);
        }
    }

}
