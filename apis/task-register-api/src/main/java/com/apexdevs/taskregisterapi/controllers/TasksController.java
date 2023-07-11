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
            // Deserializar JSON a objeto
            Tasks tasks = objectMapper.readValue(requestBody, Tasks.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error en los datos enviados");
            }

            //accountEmail = tasks.getCorreo();
            tasksRepository.save(tasks);
            return ResponseEntity.ok("Tarea registrada exitosamente");

        } catch (Exception e) {
            // Manejar la excepción en caso de que ocurra un error durante la deserialización
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos enviados");
        }
    }



    @GetMapping("/listTask")
    public ResponseEntity<String> listTasks(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserializar JSON a objeto
            Tasks tasks = objectMapper.readValue(requestBody, Tasks.class);
            accountEmail = tasks.getCorreo();
            String json="";
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error en los datos enviados");
            }

            List<Tasks> tasksListByCorreo = tasksRepository.findByCorreo(accountEmail);
            json = objectMapper.writeValueAsString(tasksListByCorreo);

            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al convertir a JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private String convertToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
