package com.apexdevs.accountcreationapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountsRepository {
    @Autowired
    private AccountsRepository accountsRepository;

    //TODO
    //Endpoints
    //name
    //email
    //password
    @GetMapping("/hello")
    public String hello() {
        return "{\"message\": \"Hola\"}";
    }

    @PostMapping("/Post")
    public String Post() {
        return "{\"message\": \"Hola\"}";
    }


}
