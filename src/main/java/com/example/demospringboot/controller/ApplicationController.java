package com.example.demospringboot.controller;

import com.example.demospringboot.model.Login;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }


    @PostMapping("/login")
    public boolean login(Login user){
        //for demo purposes this is always true
        return true;
    }


}
