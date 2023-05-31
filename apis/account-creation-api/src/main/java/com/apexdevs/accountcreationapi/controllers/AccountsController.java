package com.apexdevs.accountcreationapi.controllers;

import com.apexdevs.accountcreationapi.model.Accounts;
import com.apexdevs.accountcreationapi.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//AccountRepository -> AccountsController ----- @Controller -> RestController
@RestController
public class AccountsController {
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

    //Metodo post para realizar insercion con Json
    @PostMapping("/add")
    public ResponseEntity<String> createAccount(
            @Validated
            @RequestBody
            Accounts accounts,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos enviados");
        }
        accountsRepository.save(accounts);
        return ResponseEntity.ok("Profesor creado exitosamente");
    }


}
