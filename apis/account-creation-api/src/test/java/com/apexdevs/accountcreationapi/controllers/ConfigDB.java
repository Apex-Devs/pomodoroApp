package com.apexdevs.accountcreationapi.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class ConfigDB {

    @Value("${spring.datasource.url}")
    private String url; //="jdbc:mysql://localhost:3307/pomodoroapp?serverTimezone=UTC";
    @Value("${spring.datasource.username}")
    private String user;//="root";
    @Value("${spring.datasource.password}")
    private String password;//="123";

    //@Bean
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");//("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        return  dataSourceBuilder.build();
    }
}
