package com.apexdevs.accountverificationapi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cuentas")
public class Accounts {

    @Id
    @Column(name="correo")
    private String correo;

    @Column(name="contraseña")
    private String contraseña;

}
