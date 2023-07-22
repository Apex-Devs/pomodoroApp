package com.apexdevs.pomodororegisterapi.controllers;


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
import java.util.List;

//PomodorosRepository -> PomodorosController ----- @Controller -> RestController
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
            // Deserialization of JSON object
            Pomodoros pomodoros = objectMapper.readValue(requestBody, Pomodoros.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error in the data that has been send");
            }
            pomodorosRepository.save(pomodoros);
            return ResponseEntity.ok("Pomodoro added successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in the data that has been send");
        }
    }


    @GetMapping("/listPomodoros")
    public ResponseEntity<String> listPomodoros(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserialization of JSON object
            Pomodoros pomodoros = objectMapper.readValue(requestBody, Pomodoros.class);
            taskID = pomodoros.getFktask();
            String json="";
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error in the data that has been send");
            }

            List<Pomodoros> pomodorosListByTaskId = pomodorosRepository.findByFktask(taskID);
            json = objectMapper.writeValueAsString(pomodorosListByTaskId);

            return new ResponseEntity<>(json, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Error converting JSON", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
