package com.apexdevs.accountcreationapi.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "cuentas")
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario")
    private Integer id_usuario;

    @Column(name="nombre")
    private String nombre;

    @Column(name="correo")
    private String correo;

    @Column(name="contraseña")
    private String contraseña;

}
