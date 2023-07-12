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

    String mailBodyModify = "Enhorabuena su contraseña fue modificada";
    String mailHeaderModify = "Correo de confirmacion cambio de contraseña Pomodoro App";

    String mailBodyRecover = "Usted ha solicitado la recuperacion de su contraseña.\nEn caso de no ser asi " +
            "omita este mensaje.\n";
    String mailHeaderRecover = "Correo de recuperacion de contraseña Pomodoro App";

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
            // Deserializar JSON a objeto
            accountEmail = objectMapper.readValue(requestBody, Accounts.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error en los datos enviados");
            }

            url="http://localhost:8080/"+accountEmail.getCorreo()+"/updatePassword";
            fullMail=mailBodyRecover+url;
            enviarCorreoConfirmacion(accountEmail.getCorreo(),mailHeaderRecover,fullMail);

            return ResponseEntity.ok("Correo enviado exitosamente");

        } catch (Exception e) {
            // Manejar la excepción en caso de que ocurra un error durante la deserialización
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos enviados");
        }
    }
    //----------------------------

    //Metodo post para realizar insercion con Json

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
                        .body("Error en los datos enviados");
            }
            account.setCorreo(accounts.getCorreo());
            account.setNombre(accounts.getNombre());
            accountsRepository.save(account); //Guardamos la nueva contraseña

            enviarCorreoConfirmacion(account.getCorreo(),mailHeaderModify,mailBodyModify);

            ra.addFlashAttribute("msgExito", "Contraseña actualizada");
            return ResponseEntity.ok().body("redirect:/");
        } catch (Exception e) {
            // Manejar la excepción en caso de que ocurra un error durante la deserialización
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos enviados");
        }
    }



    //------------------------Confirmacion

    @Autowired
    private JavaMailSender javaMailSender;

    // Método para enviar un correo de confirmación
    public void enviarCorreoConfirmacion(String destinatario, String asunto, String cuerpo) {
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        try {
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mensaje);
    }

    //---------------------------------------------------------
}
