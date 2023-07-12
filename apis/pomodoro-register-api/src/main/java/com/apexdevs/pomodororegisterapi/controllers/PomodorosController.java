package com.apexdevs.pomodororegisterapi.controllers;

import com.apexdevs.accountcreationapi.model.Accounts;
import com.apexdevs.accountcreationapi.repository.AccountsRepository;
import com.apexdevs.pomodororegisterapi.model.Pomodoros;
import com.apexdevs.pomodororegisterapi.repository.PomodorosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

//AccountRepository -> AccountsController ----- @Controller -> RestController
@RestController
public class PomodorosController {
    @Autowired
    private PomodorosRepository pomodorosRepository;

    Integer taskID;
    @PostMapping("/addPomodoro")
    public ResponseEntity<String> addPomodoro(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserializar JSON a objeto
            Pomodoros pomodoros = objectMapper.readValue(requestBody, Pomodoros.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error en los datos enviados");
            }

            //accountEmail = tasks.getCorreo();
            pomodorosRepository.save(pomodoros);
            return ResponseEntity.ok("Pomodoro registrado exitosamente");

        } catch (Exception e) {
            // Manejar la excepción en caso de que ocurra un error durante la deserialización
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos enviados");
        }
    }


    @GetMapping("/listPomodoro")
    public ResponseEntity<String> listPomodoros(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserializar JSON a objeto
            Pomodoros pomodoros = objectMapper.readValue(requestBody, Pomodoros.class);
            taskID = pomodoros.getId_tarea();
            String json="";
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error en los datos enviados");
            }

            List<Pomodoros> pomodorosListByTaskId = pomodorosRepository.findById_tarea(taskID);
            json = objectMapper.writeValueAsString(pomodorosListByTaskId);

            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al convertir a JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
