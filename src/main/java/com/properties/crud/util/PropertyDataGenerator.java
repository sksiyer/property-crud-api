package com.properties.crud.util;

import com.properties.crud.data.FileHandler;
import com.properties.crud.model.Address;
import com.properties.crud.data.PropertyData;
import com.properties.crud.model.Property;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PropertyDataGenerator {


    public static List<PropertyData> generatePropertyDataList(int numberOfEntries) {
        List<PropertyData> propertyDataList = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 1; i <= numberOfEntries; i++) {
            PropertyData propertyData = createRandomPropertyData(i, faker);
            propertyDataList.add(propertyData);
        }

        return propertyDataList;
    }

    private static PropertyData createRandomPropertyData(int id, Faker faker) {
        PropertyData propertyData = new PropertyData(new Property(), id);

        Address address = new Address();
        address.setHouseNumber(faker.address().buildingNumber());
        address.setStreetName(faker.address().streetName());
        address.setPostcode(generateRandomLondonPostcode());

        propertyData.setAddress(address);
        propertyData.setNoOfBedrooms(faker.number().numberBetween(1, 5));
        propertyData.setSizeBySqrFoot(faker.number().randomDouble(2, 500, 3000));
        propertyData.setPurchasePrice(new BigDecimal(
                faker.number().randomDouble(2, 100000, 1000000)
        ).setScale(2, RoundingMode.HALF_UP));

        return propertyData;
    }
    private static String generateRandomLondonPostcode() {
        // London's area codes
        String[] londonAreaCodes = {"SE", "NW", "SW", "N", "TW", "KT", "WC", "EC", "E"};

        // Randomly select one of the London area codes
        String londonAreaCode = Faker.instance().options().option(londonAreaCodes);

        // Generate a random one or two-digit number for the second part of the postcode
        int randomDigits = Faker.instance().number().numberBetween(1, 20);

        return londonAreaCode + randomDigits + " " +
                Faker.instance().number().digits(1) +
                Faker.instance().regexify("[A-Z]{2}");
    }


    public static void main(String[] args) throws IOException {
        List<PropertyData> propertyDataList = generatePropertyDataList(600);

        // Print the generated JSON
        System.out.println("[");
        for (PropertyData propertyData : propertyDataList) {
            new FileHandler().write(propertyData);
            System.out.println("  " + propertyData.toString() + ",");
        }
        System.out.println("]");
    }
}
