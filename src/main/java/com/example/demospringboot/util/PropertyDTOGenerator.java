package com.example.demospringboot.util;

import com.example.demospringboot.data.FileHandler;
import com.example.demospringboot.model.Address;
import com.example.demospringboot.data.PropertyDTO;
import com.example.demospringboot.model.Property;
import com.github.javafaker.Faker;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class PropertyDTOGenerator {


    public static List<PropertyDTO> generatePropertyDTOList(int numberOfEntries) {
        List<PropertyDTO> propertyDTOList = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 1; i <= numberOfEntries; i++) {
            PropertyDTO propertyDTO = createRandomPropertyDTO(i, faker);
            propertyDTOList.add(propertyDTO);
        }

        return propertyDTOList;
    }

    private static PropertyDTO createRandomPropertyDTO(int id, Faker faker) {
        PropertyDTO propertyDTO = new PropertyDTO(new Property(), id);

        Address address = new Address();
        address.setHouseNumber(faker.address().buildingNumber());
        address.setStreetName(faker.address().streetName());
        address.setPostcode(generateRandomLondonPostcode());

        propertyDTO.setAddress(address);
        propertyDTO.setNoOfBedrooms(faker.number().numberBetween(1, 5));
        propertyDTO.setSizeBySqrFoot(faker.number().randomDouble(2, 500, 3000));
        propertyDTO.setPurchasePrice(new BigDecimal(
                faker.number().randomDouble(2, 100000, 1000000)
        ).setScale(2, RoundingMode.HALF_UP));

        return propertyDTO;
    }
    private static String generateRandomLondonPostcode() {
        // London area codes
        String[] londonAreaCodes = {"SE", "NW", "SW", "N", "TW", "KT", "WC", "EC", "E"};

        // Randomly select one of the London area codes
        String londonAreaCode = Faker.instance().options().option(londonAreaCodes);

        // Generate a random one or two-digit number for the second part of the postcode
        // Generate a random one or two-digit number for the second part of the postcode
        int randomDigits = Faker.instance().number().numberBetween(1, 20);


        return londonAreaCode + randomDigits + " " +
                Faker.instance().number().digits(1) +
                Faker.instance().regexify("[A-Z]{2}");
    }


    public static void main(String[] args) throws IOException {
        List<PropertyDTO> propertyDTOList = generatePropertyDTOList(600);

        // Print the generated JSON
        System.out.println("[");
        for (PropertyDTO propertyDTO : propertyDTOList) {
            new FileHandler().write(propertyDTO);
            System.out.println("  " + propertyDTO.toString() + ",");
        }
        System.out.println("]");
    }
}
