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

    String mailBody = "En hora buena su cuenta fue creada";
    String mailHeader = "Correo de confirmacion Pomodoro App";

    //Metodo post para realizar insercion con Json
    @PostMapping("/add")
    public ResponseEntity<String> createAccount(
            @RequestBody String requestBody,
            ObjectMapper objectMapper,
            BindingResult bindingResult
    ) {
        try {
            // Deserializar JSON a objeto
            Accounts accounts = objectMapper.readValue(requestBody, Accounts.class);
            if (bindingResult.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error en los datos enviados");
            }
            accountsRepository.save(accounts);

            enviarCorreoConfirmacion(accounts.getCorreo(),mailHeader,mailBody);

            return ResponseEntity.ok("Cuenta creada exitosamente");

            // Hacer algo con el objeto deserializado...
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

}
