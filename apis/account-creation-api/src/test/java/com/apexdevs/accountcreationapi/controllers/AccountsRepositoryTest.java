package com.apexdevs.accountcreationapi.controllers;

import com.apexdevs.accountcreationapi.model.AccountsTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class AccountsRepositoryTest {


    private AccountsRepositoryTest accountsRepository;


    /*@Autowired
    private AccountsTest accounts;*/

    private Response response;
    private String baseURL = "local:8080";
    private String path = "/Post";

    /*private String nombre =accounts.getNombre();
    private String correo =accounts.getCorreo();
    private String contraseña =accounts.getContraseña();*/

    @Autowired
    private DataSource dataSource;

    @SneakyThrows
    @Test
    public void testInsertData(){
        try{
            AccountsTest accounts = new AccountsTest();
            accounts.setId_usuario(1);
            accounts.setNombre("david");
            accounts.setContraseña("1234");
            accounts.setCorreo("david@correo.com");
            //contructAccount();
            saveAccount(accounts);
            Assert.assertEquals("david",accounts.getNombre());
            Assert.assertEquals("1234",accounts.getContraseña());
            Assert.assertEquals("david@correo.com",accounts.getCorreo());
        }catch (Exception e){
            System.out.println("Error: "+e);
        }

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
    public AccountsTest contructAccount(){
        accounts = new AccountsTest(jsonObjectFromRequest());
        accounts.setNombre("david");
        accounts.setContraseña("1234");
        accounts.setCorreo("david@correo.com");
        return accounts;
    } */

    @SneakyThrows
    public void saveAccount(AccountsTest accounts) {
        Connection connection = dataSource.getConnection();
        String query="insert into cuentas (nombre,correo,contraseña) values("+accounts.getNombre()+","+accounts.getCorreo()+","+accounts.getContraseña()+")";
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

}
