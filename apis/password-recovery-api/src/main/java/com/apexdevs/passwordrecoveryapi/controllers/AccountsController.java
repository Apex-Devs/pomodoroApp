package com.apexdevs.passwordrecoveryapi.controllers;


import com.apexdevs.passwordrecoveryapi.model.Accounts;
import com.apexdevs.passwordrecoveryapi.repository.AccountsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//AccountRepository -> AccountsController ----- @Controller -> RestController
@RestController
public class AccountsController {
    @Autowired
    private AccountsRepository accountsRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Accounts accountEmail;

    String mailBodyModify = "Your password has been changed";
    String mailHeaderModify = "Confirmation email, your password has changed |PomodoroApp";

    String mailBodyRecover = "You have requested recovery password.\nIn case you did not request this change" +
            "skip this message.\n";
    String mailHeaderRecover = "Recover your password |Pomodoro App";

    String url = "";
    String fullMail = "";

    //Primer endpoint -----------------
    @GetMapping("/passwordRecover")
    public ResponseEntity<String> passwordRecover(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            //Deserialization of a json object
            accountEmail = objectMapper.readValue(requestBody, Accounts.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error in the data that has been send");
            }

            url="http://localhost:8080/password-recovery-api/"+accountEmail.getEmail()+"/updatePassword";
            fullMail=mailBodyRecover+url;
            sendConfirmationEmail(accountEmail.getEmail(),mailHeaderRecover,fullMail);

            return ResponseEntity.ok("Email has been send successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in the data that has been send");
        }
    }


    @PostMapping("/{correo}/updatePassword")
    public ResponseEntity<Object> updatePassword(
            @PathVariable String correo,
            @RequestBody String requestBody,
            BindingResult bindingResult,
            RedirectAttributes ra
    ) {
        try {
            Accounts accounts = accountsRepository.findById(correo)
                    .orElseThrow(EntityNotFoundException::new);

            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
            }
            Accounts account = objectMapper.readValue(requestBody, Accounts.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error in the data that has been send");
            }
            account.setEmail(accounts.getEmail());
            account.setName(accounts.getName());
            accountsRepository.save(account); //Save the new password

            sendConfirmationEmail(account.getEmail(),mailHeaderModify,mailBodyModify);

            ra.addFlashAttribute("sucessfulMsg", "Password updated");
            return ResponseEntity.ok().body("redirect:/");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in the data that has been send");
        }
    }



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
