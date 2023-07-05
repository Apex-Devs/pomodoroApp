package com.apexdevs.accountcreationapi.model;

import lombok.Data;
import javax.persistence.*;

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
