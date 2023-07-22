package com.apexdevs.accountcreationapi.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "accounts")
public class Accounts {

    @Id
    @Column(name="email")
    private String email;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

}
