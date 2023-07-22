package com.apexdevs.taskregisterapi.controllers;


import com.apexdevs.taskregisterapi.model.Tasks;
import com.apexdevs.taskregisterapi.repository.TasksRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TasksRepository -> TasksController ----- @Controller -> RestController
@RestController
public class TasksController {

    @Autowired
    private TasksRepository tasksRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    String accountEmail;
    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserialization of JSON object
            Tasks tasks = objectMapper.readValue(requestBody, Tasks.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error in the data that has been send");
            }

            tasksRepository.save(tasks);
            return ResponseEntity.ok("Task added successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in the data that has been send");
        }
    }



    @GetMapping("/listTask")
    public ResponseEntity<String> listTasks(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserialization of JSON object
            Tasks tasks = objectMapper.readValue(requestBody, Tasks.class);
            accountEmail = tasks.getEmail();
            String json="";
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error en los datos enviados");
            }

            List<Tasks> tasksListByEmail = tasksRepository.findByEmail(accountEmail);
            json = objectMapper.writeValueAsString(tasksListByEmail);

            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error converting to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
