package com.apexdevs.accountloginapi.controllers;


import com.apexdevs.accountloginapi.model.Accounts;
import com.apexdevs.accountloginapi.repository.AccountsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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


    //Hashmap that contains the registers from the accounts table
    private static Map<String, String> users = getAccountsInfo();



    //Get Endpoint for login into pomodoroApp
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
                        .body("Error in the data that has been send");
            }

            String email = accounts.getEmail();
            String password = accounts.getPassword();

            // Verificar si el correo existe en la base de datos
            if (users.containsKey(email)) {
                // Get the password from the database
                String passwordFromRegisters = users.get(email);

                // Verify is the password that is receive is correct or wrong
                if (password.equals(passwordFromRegisters)) {
                    // Correct password
                    return ResponseEntity.ok("Correct credentials\nLogin has been successful!!!");
                } else {
                    // Password is wrong
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Wrong password");
                }
            } else {
                // Email dosnt exist in the database
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Email not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error in the data that has been send");
        }
    }



    //------------------------Confirmation email method //TODO
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


    //--------------------------------------------------------------------------------------
    public static Map<String, String> getAccountsInfo() {

        //Creation of hashmap that contains the data of the accounts table
        Map<String, String> users = new HashMap<>();

        try{
            // Get connection to the database and define the query
            DataSource connection = getConnectionDB();
            String query = "SELECT email, password FROM accounts";

            // Create the statement and execute the query
            try (PreparedStatement statement = connection.getConnection().prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery(query)) {
                // Loop through the resultSet and add the registers into the hashmap
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    users.put(email, password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
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
