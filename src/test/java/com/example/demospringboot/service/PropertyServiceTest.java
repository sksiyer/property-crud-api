package com.example.demospringboot.service;

import com.example.demospringboot.data.FileHandler;
import com.example.demospringboot.data.PropertyDTO;
import com.example.demospringboot.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class PropertyServiceTest {
    @Mock
    private FileHandler fileHandler;

    @InjectMocks
    private PropertyService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadById() {
        Property property = new Property();
        property.setNoOfBedrooms(3);
        property.setPurchasePrice(BigDecimal.valueOf(1000000.00));
        property.setSizeBySqrFoot(33.33);

        when(fileHandler.readById(anyInt())).thenReturn(new PropertyDTO(property, 45));

        PropertyDTO foundProperty = service.readById(45);

        assert(foundProperty.getId() == 45);
    }

    @Test
    void testReadByIdNotFound() {
        when(fileHandler.readById(anyInt())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.readById(45), "No property found with id: 45");
    }
}
