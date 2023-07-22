package com.apexdevs.accountcreationapi.controllers;

import com.apexdevs.accountcreationapi.model.Accounts;
import com.apexdevs.accountcreationapi.repository.AccountsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//AccountRepository -> AccountsController ----- @Controller -> RestController
@RestController
public class AccountsController {
    @Autowired
    private AccountsRepository accountsRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    String mailBody = "Congratulations! your account has been created";
    String mailHeader = "Confirmation Email from PomodoroApp";

    //Post method that receive json object and uses the information to register an account
    @PostMapping("/addAccount")
    public ResponseEntity<String> createAccount(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserialization of a json object
            Accounts accounts = objectMapper.readValue(requestBody, Accounts.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error in the data that has been send");
            }
            accountsRepository.save(accounts);

            sendConfirmationEmail(accounts.getEmail(),mailHeader,mailBody);

            return ResponseEntity.ok("Account has been created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in the data that has been send");
        }
    }

    //------------------------Confirmation email method
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendConfirmationEmail(String addressee, String subject, String body) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(addressee);
            helper.setSubject(subject);
            helper.setText(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

}
