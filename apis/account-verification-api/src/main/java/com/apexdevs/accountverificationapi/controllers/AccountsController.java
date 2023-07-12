package com.apexdevs.accountverificationapi.controllers;

import com.apexdevs.accountverificationapi.model.Accounts;
import com.apexdevs.accountverificationapi.repository.AccountsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

//AccountRepository -> AccountsController ----- @Controller -> RestController
@RestController
public class AccountsController {
    @Autowired
    private AccountsRepository accountsRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    // Simulación de la base de datos
    private static Map<String, String> usuarios = obtenerRegistrosCuentas();



    //Metodo get para realizar login
    @GetMapping("/login")
    public ResponseEntity<String> loginAccount(
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

            // Obtener los parámetros correo y contraseña
            String correo = accounts.getCorreo();//usuario.getCorreo();
            String contrasena = accounts.getContraseña();//usuario.getContrasena();

            // Verificar si el correo existe en la base de datos
            if (usuarios.containsKey(correo)) {
                // Obtener la contraseña almacenada para el correo
                String contrasenaAlmacenada = usuarios.get(correo);

                // Verificar si la contraseña recibida coincide con la almacenada
                if (contrasena.equals(contrasenaAlmacenada)) {
                    // La contraseña es correcta
                    return ResponseEntity.ok("Contraseña correcta\nInicio de sesion exitoso!!");
                } else {
                    // La contraseña es incorrecta
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Contraseña incorrecta");
                }
            } else {
                // El correo no existe en la base de datos
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Correo no encontrado");
            }
        } catch (Exception e) {
            // Manejar la excepción en caso de que ocurra un error durante la deserialización
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error en los datos enviados");
        }
    }



    //------------------------Confirmacion  //TODO

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


    //--------------------------------------------------------------------------------------
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
