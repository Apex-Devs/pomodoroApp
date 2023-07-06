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

    String mailBodyModify = "En hora buena su contraseña fue modificada";
    String mailHeaderModify = "Correo de confirmacion cambio de contraseña Pomodoro App";

    String mailBodyRecover = "Recuperacion de contraseña\nURL pendiente";
    String mailHeaderRecover = "Correo de recuperacion de contraseña Pomodoro App";

    // Simulación de la base de datos
    private static Map<String, String> usuarios = obtenerRegistrosCuentas();

    //Metodo post para realizar insercion con Json

    @GetMapping("/{correo}/edit")
    public ResponseEntity<Object> editPassword(@PathVariable String correo) {
        Accounts accounts = accountsRepository.findById(correo)
                .orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok().body(accounts);
    }

    @PostMapping("/{correo}/edit")
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

            accountsRepository.save(account);

            enviarCorreoConfirmacion(account.getCorreo(),mailHeaderModify,mailBodyModify);

            ra.addFlashAttribute("msgExito", "Correo actualizado");
            return ResponseEntity.ok().body("redirect:/");
        } catch (Exception e) {
            // Manejar la excepción en caso de que ocurra un error durante la deserialización
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos enviados");
        }
    }

    @GetMapping("/passwordRecover")
    public ResponseEntity<String> passwordRecover(
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

            enviarCorreoConfirmacion(accounts.getCorreo(),mailHeaderRecover,mailBodyRecover);

            return ResponseEntity.ok("Correo enviado exitosamente");

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
    public static Map<String, String> obtenerRegistrosCuentas() {
        // Crear el HashMap para almacenar los registros de la tabla "cuentas"
        Map<String, String> usuarios = new HashMap<>();

        try{
            // Establecer la conexión a la base de datos
            DataSource connection = getConnectionDB();
            // Crear la sentencia SQL para obtener todos los registros de la tabla "cuentas"
            String query = "SELECT correo, contraseña FROM cuentas";

            // Crear el Statement y ejecutar la consulta
            try (PreparedStatement statement = connection.getConnection().prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery(query)) {
                // Recorrer el resultado del ResultSet y agregar los registros al HashMap
                while (resultSet.next()) {
                    String correo = resultSet.getString("correo");
                    String contraseña = resultSet.getString("contraseña");
                    usuarios.put(correo, contraseña);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public static DataSource getConnectionDB(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/pomodoroapp?serverTimezone=UTC");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("david");
        return  dataSourceBuilder.build();
    }
}
