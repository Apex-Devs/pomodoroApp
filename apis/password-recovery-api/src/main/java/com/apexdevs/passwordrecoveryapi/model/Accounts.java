package com.apexdevs.passwordrecoveryapi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cuentas")
public class Accounts {

    @Id
    @Column(name="correo")
    private String correo;

    @Column(name="nombre")
    private String nombre;

    @Column(name="contraseña")
    private String contraseña;

}
