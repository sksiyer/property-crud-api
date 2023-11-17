package com.example.demospringboot.controller;

import com.example.demospringboot.model.Property;
import com.example.demospringboot.data.PropertyDTO;
import com.example.demospringboot.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {
    @Autowired
    private PropertyService service;

    @PostMapping
    public PropertyDTO create(@RequestBody Property property){
        return service.create(property);
    }

    @GetMapping("/{id}")
    public PropertyDTO readById(@PathVariable Integer id){
        return service.readById(id);
    }

    @GetMapping
    public List<PropertyDTO> readAll(){
        return service.read();
    }

    @PutMapping("/{id}")
    public PropertyDTO update(@PathVariable Integer id, @RequestBody Property property){
        return service.update(property, id);
    }

    @GetMapping("/areacode/{areacode}/average-sqrfoot-price")
    public Double getAverageSqrFootPrice(@PathVariable String areacode){
        return service.getAverageSqrFootPrice(areacode);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }


}
