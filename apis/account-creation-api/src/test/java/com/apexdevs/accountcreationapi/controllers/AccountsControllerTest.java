package com.apexdevs.accountcreationapi.controllers;

import com.apexdevs.accountcreationapi.model.AccountsTest;

import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class AccountsControllerTest{

    private Response response;
    private String baseURL = "local:8080";
    private String path = "/Post";

    private String query = "INSERT INTO cuentas (nombre, correo, contraseña) VALUES (?, ?, ?)";

    /*private String nombre =accounts.getNombre();
    private String correo =accounts.getCorreo();
    private String contraseña =accounts.getContraseña();*/


    @Test
    @SneakyThrows
    public void testInsertData(){

        AccountsTest accounts = new AccountsTest();
        //accounts.setId_usuario(1); //Opcional
        accounts.setNombre("david");
        accounts.setContraseña("1234");
        accounts.setCorreo("david@correo.com");
        //contructAccount();
        saveAccount(accounts);
        Assert.assertEquals("david@correo.com", accounts.getCorreo());

    }

   /* public Response callApiEndpoint() {
        RestAssured.baseURI = baseURL;
        RequestSpecification request = RestAssured.given();
        response = request.get(path);
        return response;
    }

    @SneakyThrows
    public JSONObject jsonObjectFromRequest(){
        JSONObject jsonObjectResponse = new JSONObject(callApiEndpoint().getBody().asString());
        return jsonObjectResponse;
    }

    @SneakyThrows
    public void saveAccount(AccountsTest accounts) {
        DataSource connection = null;
        try {
            connection = getConnectionDB();
            try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
                statement.setString(1, accounts.getNombre());
                statement.setString(2, accounts.getCorreo());
                statement.setString(3, accounts.getContraseña());
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* //Revisar por que no toma estos valores
    @Value("${spring.datasource.url}")
    private String url; //="jdbc:mysql://localhost:3307/pomodoroapp?serverTimezone=UTC";
    @Value("${spring.datasource.username}")
    private String user;//="root";
    @Value("${spring.datasource.password}")
    private String password;//="123";
     */
   @SneakyThrows
   public void saveAccount(AccountsTest accounts) {
       DataSource connection = null;
       try {
           connection = getConnectionDB();
           try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
               statement.setString(1, accounts.getNombre());
               statement.setString(2, accounts.getCorreo());
               statement.setString(3, accounts.getContraseña());
               statement.executeUpdate();
           }
       } catch (Exception e) {
           // Manejar la excepción según tus necesidades
           e.printStackTrace();
       }
   }

    public DataSource getConnectionDB(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3307/pomodoroapp?serverTimezone=UTC");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("1234");
        return  dataSourceBuilder.build();
    }

}
